package com.yj.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterRequestDto {
    private String id;
    private String name;
    private String password;
    private String email;
    private String description;
}
