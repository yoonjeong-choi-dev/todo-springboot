package com.yj.config;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;

@Slf4j
@SpringBootTest
@ContextConfiguration
public class SendgridTest {

    @Autowired
    SendGrid sendGrid;

    @Test
    public void sendEmailTest() {
        Email from = new Email("yjchoi7166@naver.com");
        Email to = new Email("yjchoi7166@gmail.com");
        String subject = "SendGrid Sending Email Test";
        Content content = new Content("text/plain", "Hello~~ This is a test mail");

        Mail mail = new Mail(from, subject, to, content);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);
            log.info("Sending to... " + to.getEmail());
            log.info(String.valueOf(response.getStatusCode()));
            log.info(String.valueOf(response.getBody()));
            log.info(String.valueOf(response.getHeaders()));
        } catch (IOException ex) {
            ex.printStackTrace();
            log.warn(ex.getMessage());
        }
    }

    @Test
    public void verifySendgrid() {
        Email from = new Email("yjchoi7166@naver.com");
        String subject = "Sending with SendGrid is Fun";
        Email to = new Email("test@example.com");
        Content content = new Content("text/plain", "and easy to do anywhere, even with Java");
        Mail mail = new Mail(from, subject, to, content);

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);
            log.info(String.valueOf(response.getStatusCode()));
            log.info(String.valueOf(response.getBody()));
            log.info(String.valueOf(response.getHeaders()));
        } catch (IOException ex) {
            ex.printStackTrace();
            log.warn(ex.getMessage());
        }
    }
}
