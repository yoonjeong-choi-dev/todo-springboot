package com.yj.web.dto.email;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailSendResultDTO {
    private int status;
    private String message;
}
