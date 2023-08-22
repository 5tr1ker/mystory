package com.team.mystory.todo.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TodoListResponse {
    private String data;

    private boolean isComplete;

    private String completedDate;

}
