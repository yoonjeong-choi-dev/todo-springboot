package com.yj.service.pubsub;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.gson.Gson;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import com.yj.config.GCPCredentialProvider;
import com.yj.web.dto.pubsub.PubSubMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Service
public class PubSubService {

    private static final Logger logger = Logger.getLogger(PubSubService.class.getName());

    @Value("${spring.cloud.gcp.project-id}")
    private String projectId;

    @Value("${spring.cloud.gcp.topic-id}")
    private String topicId;

    @Value("${spring.cloud.gcp.subscription-id}")
    private String subscriptionId;

    private final Gson gson = new Gson();

    private final GCPCredentialProvider credentialProvider;

    public String sendMessage(PubSubMessageDto message) throws InterruptedException {
        TopicName topicName = TopicName.of(projectId, topicId);

        Publisher publisher = null;
        String messageId = null;
        try {
            publisher = Publisher.newBuilder(topicName).setCredentialsProvider(credentialProvider).build();
            String jsonStr = gson.toJson(message);
            ByteString data = ByteString.copyFromUtf8(jsonStr);
            PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();

            ApiFuture<String> messageIdFuture = publisher.publish(pubsubMessage);
            messageId = messageIdFuture.get();
            logger.info("Published Message Id : " + messageId);

        } catch (Exception e) {
            e.printStackTrace();
            logger.warning(e.getMessage());
        } finally {
            if (publisher != null) {
                publisher.shutdown();
                publisher.awaitTermination(1, TimeUnit.MINUTES);
            }
        }

        return messageId;
    }

    public List<PubSubMessageDto> getMessage() {
        ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(projectId, subscriptionId);
        List<PubSubMessageDto> result = new ArrayList<>();

        MessageReceiver receiver =
                (PubsubMessage message, AckReplyConsumer consumer) -> {
                    logger.info("Id : " + message.getMessageId());
                    logger.info("Data : " + message.getData().toStringUtf8());

                    PubSubMessageDto dto = gson.fromJson(message.getData().toStringUtf8(), PubSubMessageDto.class);
                    result.add(dto);
                    consumer.ack();
                };

        Subscriber subscriber = null;

        try {
            subscriber = Subscriber.newBuilder(subscriptionName, receiver).setCredentialsProvider(credentialProvider).build();

            subscriber.startAsync().awaitRunning();
            logger.info("Listening for message on " + subscriptionName.toString());
            subscriber.awaitTerminated(30, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            logger.info("Time out for receiving");
            subscriber.stopAsync();
        }

        return result;
    }
}
