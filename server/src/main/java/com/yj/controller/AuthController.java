package com.yj.controller;

import com.yj.config.jwt.JwtTokenUtil;
import com.yj.domain.user.Member;
import com.yj.dto.member.TokenResponseDto;
import com.yj.service.member.MemberService;
import com.yj.dto.member.AuthRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final MemberService memberService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        Member member = memberService.getByCredential(request.getId(), request.getPassword());

        String token = jwtTokenUtil.generateToken(member.getId());
        TokenResponseDto response = new TokenResponseDto(token);
        return ResponseEntity.ok(response);
    }
}
