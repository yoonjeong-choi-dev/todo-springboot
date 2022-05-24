package com.yj.web;

import com.yj.service.todo.TodoService;
import com.yj.web.dto.todo.TodoCreateRequestDto;
import com.yj.web.dto.todo.TodoCreateResponseDto;
import com.yj.web.dto.todo.TodoResponseDto;

import com.yj.web.dto.todo.TodoUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/todo")
public class TodoController {
    private final TodoService todoService;

    @GetMapping("/all")
    public List<TodoResponseDto> getAllList() {
        return todoService.getAllList();
    }

    @GetMapping("/{id}")
    public List<TodoResponseDto> getListOfMember(@PathVariable(name = "id") String memberId) {
        return todoService.getList(memberId);
    }

    @PostMapping
    public ResponseEntity<TodoCreateResponseDto> create(@AuthenticationPrincipal String memberId, @RequestBody TodoCreateRequestDto requestDto) {
        TodoCreateResponseDto dto = todoService.create(memberId, requestDto.getContent());

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@AuthenticationPrincipal String memberId, @PathVariable UUID id, @RequestBody TodoUpdateRequestDto requestDto) {
        HttpStatus status;
        if (todoService.update(memberId, id, requestDto)) {
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal String memberId, @PathVariable UUID id) {
        HttpStatus status;
        if (todoService.delete(memberId, id)) {
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(status);
    }
}
