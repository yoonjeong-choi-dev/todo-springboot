package com.yj.sample;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
public class SampleControllerTest {

    private static final String baseUrl = "/sample/";

    @Autowired
    private MockMvc mvc;

    @Test
    public void getSampleString() throws Exception {
        String ret = "This is a sample response";
        mvc.perform(get(baseUrl + "sampleString"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(ret)));
    }

    @Test
    public void getSampleDto() throws Exception {
        String name = "yoonjeong-choi";
        int age = 30;

        mvc.perform(get(baseUrl + "sampleDto").param("name", name).param("age", String.valueOf(age)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.age", is(age)));

    }
}
