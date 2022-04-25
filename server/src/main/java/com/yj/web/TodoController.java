package com.yj.web;

import com.yj.service.todo.TodoService;
import com.yj.web.dto.TodoCreateRequestDto;
import com.yj.web.dto.TodoCreateResponseDto;
import com.yj.web.dto.TodoResponseDto;

import com.yj.web.dto.TodoUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/todo")
public class TodoController {
    private final TodoService todoService;

    @GetMapping
    public List<TodoResponseDto> getList() {
        return todoService.getList().stream().map(TodoResponseDto::new).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<TodoCreateResponseDto> create(@RequestBody TodoCreateRequestDto requestDto) {
        TodoCreateResponseDto dto = new TodoCreateResponseDto(todoService.create(requestDto.getContent()));

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody TodoUpdateRequestDto requestDto) {
        HttpStatus status;
        if (todoService.update(id, requestDto)) {
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        HttpStatus status;
        if (todoService.delete(id)) {
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(status);
    }
}
