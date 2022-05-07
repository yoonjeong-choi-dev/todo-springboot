package com.yj.config;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@PropertySource(value = "classpath:smsSender.properties")
public class TwilioSMSSender {

    private static final Logger logger = Logger.getLogger(TwilioSMSSender.class.getName());

    private final String senderNumber;

    public TwilioSMSSender(@Value("${twilio.sid}") String accountSid,
                           @Value("${twilio.token}") String authToken,
                           @Value("${twilio.senderNumber}") String senderNumber) {

        this.senderNumber = senderNumber;

//        logger.info("================================================");
//        logger.info(accountSid);
//        logger.info(authToken);
//        logger.info(senderNumber);
//        logger.info("================================================");

        Twilio.init(accountSid, authToken);
    }

    public void sendMessage(String phoneNumber, String message) {
        logger.info("Sending message to " + phoneNumber);
        Message client = Message.creator(
                new PhoneNumber(phoneNumber),
                new PhoneNumber(senderNumber),
                message
        ).create();
        logger.info("Success to send!");
    }

}
