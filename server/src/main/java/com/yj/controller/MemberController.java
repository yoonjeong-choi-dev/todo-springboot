package com.yj.controller;

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
    public ResponseEntity<MemberInfoResponse> getMemberData(@PathVariable String id) {
        Member member = memberService.getMemberData(id);
        if (member == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(new MemberInfoResponse(member), HttpStatus.OK);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@AuthenticationPrincipal String memberId,
                                       @PathVariable String id, @RequestBody MemberUpdateRequestDto requestDto) {

        HttpStatus status;
        if (!memberId.equals(id)) {
            status = HttpStatus.BAD_REQUEST;
        } else if (memberService.update(id, requestDto)) {
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal String memberId, @PathVariable String id) {
        HttpStatus status;
        if (!memberId.equals(id)) {
            status = HttpStatus.BAD_REQUEST;
        } else if (memberService.delete(id)) {
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(status);
    }
}
