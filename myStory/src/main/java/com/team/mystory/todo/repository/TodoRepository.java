package com.team.mystory.todo.repository;

import com.team.mystory.todo.domain.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TodoRepository extends JpaRepository<TodoList, Long>, CustomTodoListRepository {
    void deleteTodoListByTodoListId(long todoListId);

}
