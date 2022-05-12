package com.yj.service.pubsub;

import com.yj.web.dto.pubsub.PubSubMessageDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class PubSubServiceTest {

    private static final Logger logger = Logger.getLogger(PubSubService.class.getName());

    @Autowired
    PubSubService pubSubService;

    @Test
    public void publishMessageTest() throws InterruptedException {
        List<PubSubMessageDto> messageList = new ArrayList<>();
        final int testMessageNum = 5;

        for (int i = 0; i < testMessageNum; i++) {
            Map<String, String> data = new HashMap<>();
            for (int j = 0; j <= i; j++) {
                data.put("key " + j, "value " + j);
            }

            PubSubMessageDto msg = new PubSubMessageDto("Service Test", "Service Test Body " + i, "JUNIT5", data);
            messageList.add(msg);
        }

        for (PubSubMessageDto msg : messageList) {
            String id = pubSubService.sendMessage(msg);

            assertNotNull(id);
            logger.info("Result Message : " + id);
        }
    }

    @Test
    public void receiveMessageTest() {
        List<PubSubMessageDto> messageList = pubSubService.getMessage();

        assertNotNull(messageList);

        for (PubSubMessageDto msg : messageList) {
            logger.info("Title : " + msg.getTitle());
            logger.info("Body : " + msg.getBody());
            logger.info("Device : " + msg.getDeviceToken());
            logger.info("Data : " + msg.getData().toString());
        }
    }
}