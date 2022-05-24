package com.yj.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.yj.service.pubsub.PubSubService;
import com.yj.dto.pubsub.PubSubMessageDto;
import com.yj.dto.pubsub.MessageQueueSchema;
import com.yj.dto.pubsub.PushType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/localtest")
public class LocalTestController {

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
        log.info("Push Message : " + message);

        pubSubTemplate.publish(topicName, message);
        return ResponseEntity.ok("Success to publish");
    }

    @PostMapping("/pub")
    public ResponseEntity<?> pubAndSub(@RequestBody PubSubMessageDto contentDto) {
        try {
            pubSubService.sendMessage(contentDto);
            //pubSubService.getMessage();
        } catch (InterruptedException e) {
            log.warn(e.getMessage());
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
