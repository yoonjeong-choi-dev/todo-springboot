package com.yj.domain.todo;


import com.sun.istack.NotNull;
import com.yj.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@NoArgsConstructor
@ToString
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

    // TODO : user_id - 외래 키 설정

    public void update(String content, boolean completed) {
        this.content = content;
        this.completed = completed;
    }

    @Builder
    public TodoItem(String content) {
        this.content = content;
    }
}
