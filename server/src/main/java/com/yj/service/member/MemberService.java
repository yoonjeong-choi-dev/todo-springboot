package com.yj.service.member;


import com.yj.domain.user.Member;
import com.yj.domain.user.MemberRepository;
import com.yj.web.dto.member.MemberRegisterRequestDto;
import com.yj.web.dto.member.MemberUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
// 순환 참조를 막기 위해 유저 CRUD 와 스프링 시큐리티 관련 서비스를 분리
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member register(MemberRegisterRequestDto dto) {
        Member member = Member.builder().id(dto.getId()).password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName()).email(dto.getEmail()).description(dto.getDescription()).build();
        member = memberRepository.save(member);
        return member;
    }

    public Member getMemberData(String id) {
        return memberRepository.getById(id);
    }

    public List<Member> getList() {
        return memberRepository.findAll();
    }

    @Transactional
    public boolean update(String id, MemberUpdateRequestDto requestDto) {
        Member member = memberRepository.findById(id).orElse(null);

        if (member == null) return false;

        member.update(requestDto.getName(), requestDto.getDescription());

        return true;
    }

    public boolean delete(String id) {
        boolean ret = true;

        try {
            memberRepository.deleteById(id);
        } catch (IllegalArgumentException ex) {
            ret = false;
        }

        return ret;
    }
}
