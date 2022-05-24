package com.yj.dto.todo;

import com.yj.domain.todo.TodoItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class TodoResponseDto {
    private final UUID id;
    private final String content;
    private final  boolean completed;
    private final Timestamp createdAt;

    public TodoResponseDto(TodoItem entity) {
        id = entity.getId();
        content = entity.getContent();
        completed = entity.isCompleted();
        createdAt = entity.getCreatedAt();
    }
}
