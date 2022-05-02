package com.yj.domain.todo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;


import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoItemRepositoryTest {
    @Autowired
    TodoItemRepository todoItemRepository;

    @Test
    public void insertAndLoadTest() {
        String content = "Test todo!";

        todoItemRepository.save(TodoItem.builder().content(content).build());

        List<TodoItem> items = todoItemRepository.findAll();
        TodoItem item = items.get(0);
        assertThat(item.getContent()).isEqualTo(content);
    }

    @Test
    public void selectAll() {
        List<TodoItem> items = todoItemRepository.findAll();
        System.out.println(items);
    }

    @Test
    public void selectByIdTest() {
        List<TodoItem> items = todoItemRepository.findAll();
        TodoItem item = items.get(0);

        UUID id = item.getId();
        Optional<TodoItem> ret = todoItemRepository.findById(id);
        assertTrue(ret.isPresent());
        assertThat(ret.get().getContent()).isEqualTo(item.getContent());
    }

    @Test
    public void updateTest() {
        List<TodoItem> items = todoItemRepository.findAll();
        TodoItem item = items.get(0);

        UUID id = item.getId();
        Timestamp modifiedDate = item.getModifiedAt();

        item.update(item.getContent() + " Update!", !item.isCompleted());
        todoItemRepository.save(item);

        item = todoItemRepository.findById(id).orElse(null);

        assertNotNull(item);
        assertFalse(modifiedDate.equals(item.getModifiedAt()));
    }

    @Test
    public void deleteTest() {
        TodoItem toBeRemoved = todoItemRepository.save(TodoItem.builder().content("To Be Removed").build());

        todoItemRepository.deleteById(toBeRemoved.getId());

        toBeRemoved = todoItemRepository.findById(toBeRemoved.getId()).orElse(null);
        assertNull(toBeRemoved);

    }
}
