package com.yj.dto.pubsub;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class MessageQueueSchema {
    private PushType pushType;
    private String deviceToken;
    private String title;
    private String body;
    private Map<String, String> data;

    @Builder
    public MessageQueueSchema(PushType pushType, String deviceToken, String title, String body, Map<String, String> data) {
        this.pushType = pushType;
        this.deviceToken = deviceToken;
        this.title = title;
        this.body = body;
        this.data = data;
    }
}
