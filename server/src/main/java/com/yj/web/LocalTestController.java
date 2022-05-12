package com.yj.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.yj.service.pubsub.PubSubService;
import com.yj.web.dto.pubsub.PubSubMessageDto;
import com.yj.web.dto.pubsub.MessageQueueSchema;
import com.yj.web.dto.pubsub.PushType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RequiredArgsConstructor
@RestController
@RequestMapping("/localtest")
public class LocalTestController {

    private static final Logger logger = Logger.getLogger(LocalTestController.class.getName());

    private final PubSubTemplate pubSubTemplate;
    private final ObjectMapper objectMapper;

    private final PubSubService pubSubService;

    @Value("${spring.cloud.gcp.topic-name}")
    private String topicName;

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody PubSubMessageDto contentDto)
            throws JsonProcessingException {

        MessageQueueSchema queueSchema = MessageQueueSchema.builder()
                .pushType(PushType.SINGLE_MESSAGE)
                .deviceToken(contentDto.getDeviceToken())
                .title(contentDto.getTitle())
                .body(contentDto.getBody())
                .data(contentDto.getData())
                .build();

        String message = objectMapper.writeValueAsString(queueSchema);
        logger.info("Push Message : " + message);

        pubSubTemplate.publish(topicName, message);
        return ResponseEntity.ok("Success to publish");
    }

    @PostMapping("/pub")
    public ResponseEntity<?> pubAndSub(@RequestBody PubSubMessageDto contentDto) {
        try {
            pubSubService.sendMessage(contentDto);
            //pubSubService.getMessage();
        } catch (InterruptedException e) {
            logger.warning(e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.ok("Success to publish");
    }

    @GetMapping("/sub")
    public ResponseEntity<?> sub() {
        List<PubSubMessageDto> ret = pubSubService.getMessage();
        return ResponseEntity.ok(ret);
    }

}
