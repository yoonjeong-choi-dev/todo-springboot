package com.yj.dto.pubsub;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Nullable;
import java.util.Map;

@Getter
@NoArgsConstructor
@Setter
public class PubSubMessageDto {
    private String title;
    private String body;
    private String deviceToken;

    @Nullable
    private Map<String, String> data;

    public PubSubMessageDto(String title, String body, String deviceToken) {
        this.title = title;
        this.body = body;
        this.deviceToken = deviceToken;
    }

    public PubSubMessageDto(String title, String body, String deviceToken, @Nullable Map<String, String> data) {
        this.title = title;
        this.body = body;
        this.deviceToken = deviceToken;
        this.data = data;
    }
}
