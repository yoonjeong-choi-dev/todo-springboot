package com.yj.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration
public class TwilioTest {

    @Autowired
    TwilioSMSSender twilioSMSSender;

    @Test
    public void smsSendTest() {
        String phoneNumber = "+821071660035";
        String message = "테스트 메시지 from spring boot";
        twilioSMSSender.sendMessage(phoneNumber, message);
        twilioSMSSender.sendMessage(phoneNumber, "[Another] " + message);
    }
}
