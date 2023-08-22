package com.team.mystory.todo.controller;

import com.team.mystory.todo.dto.TodoListRequest;
import com.team.mystory.todo.dto.TodoListResponse;
import com.team.mystory.todo.service.TodoListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/todo")
public class TodoListController {

    private final TodoListService todoListService;

    @PostMapping
    public ResponseEntity saveTodoList(@CookieValue String accessToken , @RequestBody TodoListRequest todoListRequest) throws AccountException {
        todoListService.saveTodoList(accessToken , todoListRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{todoListId}")
    public ResponseEntity deleteTodoList(@PathVariable long todoListId) {
        todoListService.deleteTodoList(todoListId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{todoListId}")
    public ResponseEntity modifyTodoList(@PathVariable long todoListId , @RequestBody TodoListRequest todoListRequest) {
        todoListService.modifyTodoList(todoListId , todoListRequest);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{todoListId}/complete")
    public ResponseEntity modifyTodoListIsComplete(@PathVariable long todoListId) {
        todoListService.modifyTodoListIsComplete(todoListId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/progress")
    public ResponseEntity findInProgressTodoListUserId(@CookieValue String accessToken) {
        List<TodoListResponse> result = todoListService.findInProgressTodoListUserId(accessToken);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/achieve")
    public ResponseEntity findAchievedTodoListByUserId(@CookieValue String accessToken) {
        List<TodoListResponse> result = todoListService.findAchievedTodoListByUserId(accessToken);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/achieve/date")
    public ResponseEntity findTodoListByDateAchieved(@CookieValue String accessToken , @RequestBody TodoListRequest todoListRequest) {
        List<TodoListResponse> result = todoListService.findTodoListByDateAchieved(accessToken , todoListRequest);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/achieve/month")
    public ResponseEntity getTodoListForASpecificMonth(@CookieValue String accessToken , @RequestBody TodoListRequest todoListRequest) {
        List<TodoListResponse> result = todoListService.getTodoListForASpecificMonth(accessToken , todoListRequest);

        return ResponseEntity.ok().body(result);
    }

}