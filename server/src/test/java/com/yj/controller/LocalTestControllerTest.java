package com.yj.controller;

import com.google.gson.Gson;
import com.yj.dto.pubsub.PubSubMessageDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.logging.Logger;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LocalTestControllerTest {

    private static final Logger logger = Logger.getLogger(LocalTestControllerTest.class.getName());

    private static final String baseUrl = "/localtest/";

    private static final Gson gson = new Gson();

    @Autowired
    private MockMvc mockMvc;


    @Test
    void sendMessageTest() {
        PubSubMessageDto request = new PubSubMessageDto();
        request.setTitle("Push Title");
        request.setBody("Push Body");
        request.setDeviceToken("Mock Test Token");


        String jsonStr = gson.toJson(request);
        logger.info("Request JSON : " + jsonStr);

        try {
            mockMvc.perform(
                            post(baseUrl + "send").contentType(MediaType.APPLICATION_JSON).content(jsonStr))
                    .andExpect(status().isOk())
                    .andDo(print());
        } catch (Exception e) {
            e.printStackTrace();
            logger.warning(e.getMessage());
        }
    }
}