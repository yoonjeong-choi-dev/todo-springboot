package com.yj.web;

import com.yj.service.member.MemberService;
import com.yj.web.dto.member.MemberInfoResponse;
import com.yj.web.dto.member.MemberRegisterRequestDto;
import com.yj.web.dto.member.MemberUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody MemberUpdateRequestDto requestDto) {
        checkCurrentUser(id);

        HttpStatus status;
        if (memberService.update(id, requestDto)) {
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id)  {
        checkCurrentUser(id);
        HttpStatus status;
        if (memberService.delete(id)) {
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(status);
    }

    private void checkCurrentUser(String userId) throws InsufficientAuthenticationException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!userDetails.getUsername().equals(userId)){
            throw new InsufficientAuthenticationException("Unmatched User Id");
        }
    }

}
