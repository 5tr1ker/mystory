package com.team.mystory.todo.domain;

import com.team.mystory.account.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@Table(name = "todo_list_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class TodoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todoListId;

    private String data;

    @ManyToOne(fetch = FetchType.LAZY)
    private User userId;

    private String completedDate;

    private Boolean isComplete;

    public void updateData(String data) {
        this.data = data;
    }

    public void updateIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public static TodoList of(String data , User user) {
        return TodoList.builder()
                .data(data)
                .userId(user)
                .isComplete(false)
                .build();
    }

}