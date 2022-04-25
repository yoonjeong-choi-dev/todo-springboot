package com.yj.web.dto;

import com.yj.domain.todo.TodoItem;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
public class TodoCreateResponseDto {
    private final UUID id;
    private final Timestamp createdDate;

    public TodoCreateResponseDto(TodoItem entity) {
        id = entity.getId();
        createdDate = entity.getCreatedDate();
    }
}
