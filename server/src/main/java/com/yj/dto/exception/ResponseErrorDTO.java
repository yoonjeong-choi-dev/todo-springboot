package com.yj.dto.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ResponseErrorDTO {
    private String message;
    private HttpStatus status;
    private String type;
    private Timestamp timestamp;
}
