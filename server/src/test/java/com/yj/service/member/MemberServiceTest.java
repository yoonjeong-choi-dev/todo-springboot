package com.yj.service.member;

import com.yj.domain.todo.TodoItem;
import com.yj.domain.user.Member;
import com.yj.service.todo.TodoService;
import com.yj.web.dto.member.MemberRegisterRequestDto;
import com.yj.web.dto.todo.TodoResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;


import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Autowired
    TodoService todoService;

    @Test
    public void registerMemberTest() {
        MemberRegisterRequestDto request = new MemberRegisterRequestDto("test-id", "test-name", "test-password", "test-email", "test-desc");
        Member member = memberService.register(request);

        assertNotNull(member);
        assertEquals(member.getId(), request.getId());
        assertEquals(member.getName(), request.getName());
        assertEquals(member.getEmail(), request.getEmail());
        assertEquals(member.getDescription(), request.getDescription());
        assertNotEquals(member.getPassword(), request.getPassword());

        // 테스트 데이터 삭제
        memberService.delete(member.getId());
    }

    @Test
    @Transactional
    public void deleteMemberTest() {
        MemberRegisterRequestDto request = new MemberRegisterRequestDto("test-id", "test-name", "test-password", "test-email", "test-desc");
        Member member = memberService.register(request);

        assertNotNull(member);

        int insertTodoNum = 5;

        for (int i = 0; i < insertTodoNum; i++) {
            todoService.create(member, String.format("Test Todo %d", i + 1));
        }

        memberService.delete(request.getId());

        try {
            Member deleteMember = memberService.getMemberData(request.getId());
            assertNull(deleteMember);
        } catch (Exception ex){
            System.out.println("=============================================");
            System.out.println(ex.getMessage());
            System.out.println(ex.getClass().getName());
            System.out.println("=============================================");
        }



        try {
            List<TodoResponseDto> deleteTodos = todoService.getList(request.getId());
            assertEquals(0, deleteTodos.size());
        } catch (Exception ex){
            System.out.println("=============================================");
            System.out.println(ex.getMessage());
            System.out.println(ex.getClass().getName());
            System.out.println("=============================================");
        }

    }
}