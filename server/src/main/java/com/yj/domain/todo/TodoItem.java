package com.yj.domain.todo;


import com.sun.istack.NotNull;
import com.yj.domain.BaseTimeEntity;
import com.yj.domain.user.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class TodoItem extends BaseTimeEntity {
    @Id
    @GenericGenerator(name = "uui2", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Type(type = "true_false")
    @NotNull
    @ColumnDefault("false")
    private boolean completed;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    public void update(String content, boolean completed) {
        this.content = content;
        this.completed = completed;
    }

    @Builder
    public TodoItem(String content, Member member) {
        this.content = content;
        this.member = member;
    }
}
