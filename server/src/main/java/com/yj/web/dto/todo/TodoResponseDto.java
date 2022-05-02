package com.yj.web.dto.todo;

import com.yj.domain.todo.TodoItem;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
public class TodoResponseDto {
    private final UUID id;
    private final String content;
    private final  boolean completed;
    private final Timestamp createdDate;

    public TodoResponseDto(TodoItem entity) {
        id = entity.getId();
        content = entity.getContent();
        completed = entity.isCompleted();
        createdDate = entity.getCreatedAt();
    }
}
