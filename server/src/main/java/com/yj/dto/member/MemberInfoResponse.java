package com.yj.dto.member;

import com.yj.domain.user.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoResponse {
    private String id;
    private String name;
    private String email;
    private String description;

    public MemberInfoResponse(Member entity) {
        id = entity.getId();
        name = entity.getName();
        email = entity.getEmail();
        description = entity.getDescription();
    }
}
