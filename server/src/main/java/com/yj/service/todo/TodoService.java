package com.yj.service.todo;

import com.yj.domain.todo.TodoItem;
import com.yj.domain.todo.TodoItemRepository;
import com.yj.web.dto.todo.TodoUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TodoService {
    private final TodoItemRepository todoItemRepository;

    public List<TodoItem> getList() {
        return todoItemRepository.findAll();
    }

    public TodoItem create(String content) {
        TodoItem item = TodoItem.builder().content(content).build();
        return todoItemRepository.save(item);
    }

    @Transactional
    public boolean update(UUID id, TodoUpdateRequestDto requestDto) {
        TodoItem item = todoItemRepository.findById(id).orElse(null);

        if (item == null) return false;

        item.update(requestDto.getContent(), requestDto.isCompleted());

        return true;
    }

    public boolean delete(UUID id) {
        boolean ret = true;

        try {
            todoItemRepository.deleteById(id);
        } catch (IllegalArgumentException ex) {
            ret = false;
        }

        return ret;
    }


}
