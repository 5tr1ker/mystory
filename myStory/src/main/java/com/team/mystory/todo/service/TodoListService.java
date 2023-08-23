package com.team.mystory.todo.service;

import com.team.mystory.account.user.domain.User;
import com.team.mystory.account.user.repository.LoginRepository;
import com.team.mystory.security.jwt.support.JwtTokenProvider;
import com.team.mystory.todo.domain.TodoList;
import com.team.mystory.todo.dto.TodoListRequest;
import com.team.mystory.todo.dto.TodoListResponse;
import com.team.mystory.todo.exception.TodoListException;
import com.team.mystory.todo.repository.TodoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TodoListService {

    private final TodoRepository todoRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final LoginRepository loginRepository;

    @Transactional
    public void saveTodoList(String accessToken , TodoListRequest todoListRequest) throws AccountException {
        String userPk = jwtTokenProvider.getUserPk(accessToken);
        User user = loginRepository.findById(userPk)
                .orElseThrow(() -> new AccountException("사용자 정보를 찾을 수 없습니다."));

        TodoList todoList = TodoList.of(todoListRequest.getData() , user);

        todoRepository.save(todoList);
    }

    @Transactional
    public void deleteTodoList(long todoListId) {
        todoRepository.deleteTodoListByTodoListId(todoListId);
    }

    @Transactional
    public void modifyTodoList(long todoListId , TodoListRequest todoListRequest) {
        TodoList todoList = todoRepository.findById(todoListId)
                .orElseThrow(() -> new TodoListException("해당 데이터를 찾을 수 없습니다."));

        todoList.updateData(todoListRequest.getData());
    }

    @Transactional
    public void modifyTodoListIsComplete(long todoListId) {
        TodoList todoList = todoRepository.findById(todoListId)
                .orElseThrow(() -> new TodoListException("해당 데이터를 찾을 수 없습니다."));

        todoList.updateIsComplete(true);
    }

    public List<TodoListResponse> findInProgressTodoListUserId(String accessToken) {
        String userPk = jwtTokenProvider.getUserPk(accessToken);

        return todoRepository.findUnfinishedTodoListByUserId(userPk);
    }

    public List<TodoListResponse> findAchievedTodoListByUserId(String accessToken) {
        String userPk = jwtTokenProvider.getUserPk(accessToken);

        return todoRepository.findFinishedTodoListByUserId(userPk);
    }

    public List<TodoListResponse> findTodoListByDateAchieved(String accessToken , TodoListRequest todoListRequest) {
        String userPk = jwtTokenProvider.getUserPk(accessToken);

        return todoRepository.findTodoListInSpecificDateByCompletedDateAndUserId(userPk , todoListRequest.getData());
    }

    public List<TodoListResponse> getTodoListForASpecificMonth(String accessToken , TodoListRequest todoListRequest) {
        String userPk = jwtTokenProvider.getUserPk(accessToken);

        return todoRepository.findTodoListInSpecificMonthByCompletedDateAndUserId(userPk , todoListRequest.getData());
    }

}
