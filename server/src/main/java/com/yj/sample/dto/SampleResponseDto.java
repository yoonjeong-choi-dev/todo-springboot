package com.yj.sample.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// getter 메서드 생성
@Getter
// final 필드가 포함된 생성자 생성
@RequiredArgsConstructor
public class SampleResponseDto {
    private final String name;
    private final int age;
}
