package com.team.mystory.todo.repository;

import com.team.mystory.todo.dto.TodoListResponse;

import java.util.List;

public interface CustomTodoListRepository {
    List<TodoListResponse> findInProgressTodoListUserId(String userPk);

    List<TodoListResponse> findAchievedTodoListByUserId(String userPk);

    List<TodoListResponse> getTodoListByDateAchieved(String userPk , String date);

    List<TodoListResponse> getTodoListForASpecificMonth(String userPk , String date);

}
