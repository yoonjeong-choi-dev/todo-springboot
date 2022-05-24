package com.yj.config;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Slf4j
@Component
@PropertySource(value = "classpath:smsSender.properties")
public class TwilioSMSSender {

    private final String senderNumber;

    public TwilioSMSSender(@Value("${twilio.sid}") String accountSid,
                           @Value("${twilio.token}") String authToken,
                           @Value("${twilio.senderNumber}") String senderNumber) {

        this.senderNumber = senderNumber;

//        log.info("================================================");
//        log.info(accountSid);
//        log.info(authToken);
//        log.info(senderNumber);
//        log.info("================================================");

        Twilio.init(accountSid, authToken);
    }

    public void sendMessage(String phoneNumber, String message) {
        log.info("Sending message to " + phoneNumber);
        Message client = Message.creator(
                new PhoneNumber(phoneNumber),
                new PhoneNumber(senderNumber),
                message
        ).create();
        log.info("Success to send!");
    }

}
