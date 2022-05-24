package com.yj.service.member;


import com.yj.common.exceptions.ContentNotFoundException;
import com.yj.common.exceptions.InvalidRequestException;
import com.yj.common.exceptions.UserAlreadyExistException;
import com.yj.common.exceptions.UserAuthenticationException;
import com.yj.domain.todo.TodoItemRepository;
import com.yj.domain.user.Member;
import com.yj.domain.user.MemberRepository;
import com.yj.dto.member.MemberRegisterRequestDto;
import com.yj.dto.member.MemberUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
// 순환 참조를 막기 위해 유저 CRUD 와 스프링 시큐리티 관련 서비스를 분리
public class MemberService {

    private final TodoItemRepository todoItemRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member register(MemberRegisterRequestDto dto) {
        String memberId = dto.getId();
        String email = dto.getEmail();
        String password = dto.getPassword();

        if (memberId == null || email == null || password == null) {
            throw new InvalidRequestException("There is wrong request data in (id, email, password");
        }

        if (memberRepository.existsById(memberId)) {
            throw new UserAlreadyExistException("User id already exists : " + memberId);
        } else if (memberRepository.existsByEmail(email)) {
            throw new UserAlreadyExistException("Email already exists : " + email);
        }

        Member member = Member.builder().id(dto.getId()).password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName()).email(dto.getEmail()).description(dto.getDescription()).build();
        member = memberRepository.save(member);
        return member;
    }

    public Member getMemberData(String id) {
        Member member = memberRepository.findById(id).orElse(null);
        if (member == null) {
            throw new ContentNotFoundException("There is no user with id : " + id);
        }

        return memberRepository.getById(id);
    }

    @Transactional
    public Member getByCredential(String id, String password) {
        Member member = memberRepository.findById(id).orElse(null);

        // 유저의 비밀번호 검증
        if (member == null) {
            throw new ContentNotFoundException("There is no user with id : " + id);
        } else if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new UserAuthenticationException("Password unmatched");
        }

        return member;
    }

    public List<Member> getList() {
        return memberRepository.findAll();
    }

    @Transactional
    public void update(String id, MemberUpdateRequestDto requestDto) {
        Member member = memberRepository.findById(id).orElse(null);

        if (member == null)
            throw new ContentNotFoundException("There is no user with id to update : " + id);

        member.update(requestDto.getName(), requestDto.getDescription());
    }

    @Transactional
    public void delete(String id) {
        todoItemRepository.deleteByMemberId(id);
        try {
            memberRepository.deleteById(id);
        } catch (IllegalArgumentException ex) {
            throw new ContentNotFoundException("There is no user with id to delete : " + id);
        }
    }
}
