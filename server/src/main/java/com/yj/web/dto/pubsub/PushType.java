package com.yj.web.dto.pubsub;

import java.util.Arrays;

public enum PushType {
    SINGLE_MESSAGE(0),
    MULTIPLE_MESSAGE(1),
    ;

    public static final int SINGLE_MESSAGE_VALUE = 0;
    public static final int MULTIPLE_MESSAGE_VALUE = 1;

    private final int value;

    PushType(int value) {
        this.value = value;
    }

    public static PushType of(int value) {
        return Arrays.stream(PushType.values())
                .filter(it -> it.value == value)
                .findFirst()
                .orElse(null);
    }

    public final int getValue() {
        return value;
    }
}
