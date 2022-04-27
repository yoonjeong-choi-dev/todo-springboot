package com.yj.web.dto.todo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TodoUpdateRequestDto {
    private String content;
    private boolean completed;
}
