package com.yj.controller;

import com.yj.service.todo.TodoService;
import com.yj.dto.todo.TodoCreateRequestDto;
import com.yj.dto.todo.TodoCreateResponseDto;
import com.yj.dto.todo.TodoResponseDto;

import com.yj.dto.todo.TodoUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/todo")
public class TodoController {
    private final TodoService todoService;

    @GetMapping("/all")
    public List<TodoResponseDto> getAllList() {
        return todoService.getAllList();
    }

    @GetMapping
    public List<TodoResponseDto> getListOfMember(@AuthenticationPrincipal String memberId) {
        log.info("Get todo list owned by " + memberId);
        return todoService.getList(memberId);
    }

    @PostMapping
    public ResponseEntity<TodoCreateResponseDto> create(@AuthenticationPrincipal String memberId, @RequestBody TodoCreateRequestDto requestDto) {
        TodoCreateResponseDto dto = todoService.create(memberId, requestDto.getContent());

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@AuthenticationPrincipal String memberId, @PathVariable UUID id, @RequestBody TodoUpdateRequestDto requestDto) {
        todoService.update(memberId, id, requestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal String memberId, @PathVariable UUID id) {
        todoService.delete(memberId, id);
        return ResponseEntity.noContent().build();
    }
}
