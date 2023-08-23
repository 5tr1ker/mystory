package com.team.mystory.todo.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.mystory.todo.dto.TodoListResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.team.mystory.account.user.domain.QUser.user;
import static com.team.mystory.todo.domain.QTodoList.todoList;

@RequiredArgsConstructor
public class TodoListRepositoryImpl implements CustomTodoListRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<TodoListResponse> findUnfinishedTodoListByUserId(String userPk) {
        return jpaQueryFactory.select(Projections.constructor(
                        TodoListResponse.class,
                        todoList.data,
                        todoList.isComplete,
                        todoList.completedDate
                )).from(todoList)
                .innerJoin(todoList.userId , user).on(user.id.eq(userPk))
                .where(todoList.isComplete.eq(false))
                .fetch();
    }

    @Override
    public List<TodoListResponse> findFinishedTodoListByUserId(String userPk) {
        return jpaQueryFactory.select(Projections.constructor(
                        TodoListResponse.class,
                        todoList.data,
                        todoList.isComplete,
                        todoList.completedDate
                )).from(todoList)
                .innerJoin(todoList.userId , user).on(user.id.eq(userPk))
                .where(todoList.isComplete.eq(true))
                .fetch();
    }

    @Override
    public List<TodoListResponse> findTodoListInSpecificDateByCompletedDateAndUserId(String userPk, String date) {
        return jpaQueryFactory.select(Projections.constructor(
                        TodoListResponse.class,
                        todoList.data,
                        todoList.isComplete,
                        todoList.completedDate
                )).from(todoList)
                .innerJoin(todoList.userId , user).on(user.id.eq(userPk))
                .where(todoList.isComplete.eq(true).and(todoList.completedDate.eq(date)))
                .fetch();
    }

    @Override
    public List<TodoListResponse> findTodoListInSpecificMonthByCompletedDateAndUserId(String userPk, String date) {
        return jpaQueryFactory.select(Projections.constructor(
                        TodoListResponse.class,
                        todoList.data,
                        todoList.isComplete,
                        todoList.completedDate
                )).from(todoList)
                .innerJoin(todoList.userId , user).on(user.id.eq(userPk))
                .where(todoList.isComplete.eq(true).and(todoList.completedDate.startsWith(date)))
                .fetch();
    }
}