package com.yj.sample;

import com.yj.sample.dto.SampleResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sample")
public class SampleController {
    @GetMapping("sampleString")
    public String sample() {
        return "This is a sample response";
    }

    @GetMapping("sampleDto")
    public SampleResponseDto sampleDto(
            @RequestParam("name") String name,
            @RequestParam("age") int age
    ) {
        return new SampleResponseDto(name, age);
    }
}
