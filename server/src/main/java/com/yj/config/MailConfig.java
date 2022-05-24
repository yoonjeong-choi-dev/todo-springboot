package com.yj.config;

import com.sendgrid.SendGrid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Slf4j
@Configuration
@PropertySource(value = "classpath:emailSender.properties")
public class MailConfig {


    @Value("${sender.host}")
    private String host;

    @Value("${sender.port}")
    private int port;

    @Value("${sender.username}")
    private String username;

    @Value("${sender.password}")
    private String password;

    @Value("${prop.protocol}")
    private String protocol;

    @Value("${prop.smtp.auth}")
    private String auth;

    @Value("${prop.smtp.tls}")
    private String tls;

    @Value("${prop.debug}")
    private String debug;

    @Bean
    public JavaMailSender getJavaMailSender() {

//        log.info("================================================");
//        log.info(host);
//        log.info(username);
//        log.info(protocol);
//        log.info(auth);
//        log.info(tls);
//        log.info("================================================");

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // Set Host and Sender
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        // Set props
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.starttls.enable", tls);
        props.put("mail.transport.protocol", protocol);
        props.put("mail.smtp.auth", auth);
        props.put("mail.debug", debug);


        return mailSender;
    }

    @Value("${SENDGRID_API_KEY}")
    private String sendgridApiKey;

    @Bean
    public SendGrid getSendGrid() {
        return new SendGrid(sendgridApiKey);
    }
}
