package com.yj.domain.todo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

// TodoItem 객체를 이용하여 DB에 접근하게 해주는 인터페이스
// MyBatis mapper 인터페이스 역할
public interface TodoItemRepository extends JpaRepository<TodoItem, UUID> {
}
