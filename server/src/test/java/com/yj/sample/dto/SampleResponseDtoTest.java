package com.yj.sample.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SampleResponseDtoTest {
    @Test
    public void lombokTest() {
        String name = "yjchoi";
        int age = 30;

        SampleResponseDto dto = new SampleResponseDto(name, age);

        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAge()).isEqualTo(age);
    }
}