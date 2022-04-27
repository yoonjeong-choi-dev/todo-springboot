package com.yj.domain.user;

import com.yj.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class Member extends BaseTimeEntity {
    @Id
    private String id;

    private String name;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String email;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Builder
    public Member(String id, String name, String password, String email, String description) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.description = description;
    }

    public void update(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
