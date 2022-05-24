package com.yj.controller;

import com.yj.common.exceptions.NotAuthorizedException;
import com.yj.domain.user.Member;
import com.yj.service.member.MemberService;
import com.yj.dto.member.MemberInfoResponse;
import com.yj.dto.member.MemberRegisterRequestDto;
import com.yj.dto.member.MemberUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/list")
    public ResponseEntity<?> getList() {
        return ResponseEntity.ok(memberService.getList().stream().map(MemberInfoResponse::new).collect(Collectors.toList()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody MemberRegisterRequestDto request) {
        memberService.register(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberInfoResponse> getMemberData(@AuthenticationPrincipal String memberId, @PathVariable String id) {
        Member member = memberService.getMemberData(id);
        return new ResponseEntity<>(new MemberInfoResponse(member), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@AuthenticationPrincipal String memberId,
                                    @PathVariable String id, @RequestBody MemberUpdateRequestDto requestDto) {

        if (!memberId.equals(id)) {
            throw new NotAuthorizedException("You are not the user to update");
        }
        memberService.update(id, requestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal String memberId, @PathVariable String id) {
        if (!memberId.equals(id)) {
            throw new NotAuthorizedException("You are not the user to delete");
        }

        memberService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
