package com.yj.config;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;
import java.util.logging.Logger;

@Configuration
@PropertySource(value = "classpath:emailSender.properties")
public class MailConfig {

    private static final Logger logger = Logger.getLogger(MailConfig.class.getName());

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

//        logger.info("================================================");
//        logger.info(host);
//        logger.info(username);
//        logger.info(protocol);
//        logger.info(auth);
//        logger.info(tls);
//        logger.info("================================================");

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
    private String sendgrid_api_key;

    @Bean
    public SendGrid getSendGrid() {
        return new SendGrid(sendgrid_api_key);
    }
}
