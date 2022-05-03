package com.yj.service.todo;

import com.yj.domain.todo.TodoItem;
import com.yj.domain.todo.TodoItemRepository;
import com.yj.domain.user.Member;
import com.yj.web.dto.todo.TodoCreateResponseDto;
import com.yj.web.dto.todo.TodoResponseDto;
import com.yj.web.dto.todo.TodoUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TodoService {

    private static final Logger logger = Logger.getLogger(TodoService.class.getName());

    private final TodoItemRepository todoItemRepository;
    private final JedisPool jedisPool;

    public List<TodoResponseDto> getAllList() {
        return todoItemRepository.findAll().stream().map(TodoResponseDto::new).collect(Collectors.toList());
    }

    public List<TodoResponseDto> getList(String memberId) {
        List<TodoResponseDto> ret;
        String modifiedKey = redisModifiedKey(memberId);
        Jedis jedis = jedisPool.getResource();
        if (jedis.exists(modifiedKey) && jedis.get(modifiedKey).equals("0")) {
            // 캐시 데이터 사용
            logger.info("Use Cache Data");
            ret = getTodoListFromRedis(jedis, memberId);
        } else {
            // DB 조회 및 캐시 저장
            ret = todoItemRepository.findByMemberId(memberId).stream().map(TodoResponseDto::new).collect(Collectors.toList());

            logger.info("Save Data to Cache");
            deleteTodoListFromRedis(jedis, memberId);
            saveTodoListToRedis(jedis, memberId, ret);
            jedis.set(modifiedKey, "0");
        }

        return ret;
    }

    private List<TodoResponseDto> getTodoListFromRedis(Jedis jedis, String memberId) {
        String todoBaseKey = getTodoKey(memberId);
        Set<String> todoKeys = jedis.keys(todoBaseKey + "*");

        List<TodoResponseDto> ans = new ArrayList<>(todoKeys.size());
        for (String key : todoKeys) {
            TodoResponseDto todo = new TodoResponseDto(
                    UUID.fromString(jedis.hget(key, "id")),
                    jedis.hget(key, "content"),
                    Boolean.parseBoolean(jedis.hget(key, "completed")),
                    Timestamp.valueOf(jedis.hget(key, "createdAt"))
            );
            ans.add(todo);
        }

        return ans;
    }

    private void deleteTodoListFromRedis(Jedis jedis, String memberId) {
        logger.info("Delete All Todo List From Redis");
        String todoBaseKey = getTodoKey(memberId);
        Set<String> todoKeys = jedis.keys(todoBaseKey + "*");

        // 트랜잭션을 이용하여 한 번의 접속으로 모두 삭제
        Transaction transaction = jedis.multi();
        for (String key : todoKeys) transaction.del(key);
        transaction.exec();
    }

    private void saveTodoListToRedis(Jedis jedis, String memberId, List<TodoResponseDto> todos) {
        logger.info("Save Todo List To Redis");
        String todoBaseKey = getTodoKey(memberId);

        int curIdx = 0;
        Transaction transaction = jedis.multi();
        for (TodoResponseDto todo : todos) {
            String key = todoBaseKey + curIdx;
            transaction.hset(key, "id", todo.getId().toString());
            transaction.hset(key, "content", todo.getContent());
            transaction.hset(key, "completed", String.valueOf(todo.isCompleted()));
            transaction.hset(key, "createdAt", todo.getCreatedAt().toString());
            curIdx++;
        }

        transaction.exec();
    }

    private String getTodoKey(String memberId) {
        return memberId + ":todo:";
    }

    public TodoCreateResponseDto create(Member member, String content) {
        TodoItem item = TodoItem.builder().content(content).member(member).build();
        updateRedisModifiedKey(member.getId());

        return new TodoCreateResponseDto(todoItemRepository.save(item));
    }

    @Transactional
    public boolean update(Member member, UUID id, TodoUpdateRequestDto requestDto) {
        TodoItem item = todoItemRepository.findById(id).orElse(null);
        if (item == null || !item.getMember().getId().equals(member.getId())) return false;

        item.update(requestDto.getContent(), requestDto.isCompleted());
        updateRedisModifiedKey(member.getId());

        return true;
    }

    public boolean delete(Member member, UUID id) {
        TodoItem item = todoItemRepository.findById(id).orElse(null);
        if (item == null || !item.getMember().getId().equals(member.getId())) return false;

        todoItemRepository.deleteById(id);
        updateRedisModifiedKey(member.getId());

        return true;
    }


    private void updateRedisModifiedKey(String memberId) {
        String modifiedKey = redisModifiedKey(memberId);
        Jedis jedis = jedisPool.getResource();
        if (jedis.exists(modifiedKey)) {
            jedis.set(modifiedKey, "1");
        }
    }

    private String redisModifiedKey(String memberId) {
        return memberId + ":Modified";
    }
}
