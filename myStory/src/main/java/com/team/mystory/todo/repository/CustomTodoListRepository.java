package com.team.mystory.todo.repository;

import com.team.mystory.todo.dto.TodoListResponse;

import java.util.List;

public interface CustomTodoListRepository {
    List<TodoListResponse> findUnfinishedTodoListByUserId(String userPk);

    List<TodoListResponse> findFinishedTodoListByUserId(String userPk);

    List<TodoListResponse> findTodoListInSpecificDateByCompletedDateAndUserId(String userPk , String date);

    List<TodoListResponse> findTodoListInSpecificMonthByCompletedDateAndUserId(String userPk , String date);

}
