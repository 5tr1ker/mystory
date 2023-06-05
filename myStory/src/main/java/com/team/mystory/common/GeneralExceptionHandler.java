package com.team.mystory.common;

import com.team.mystory.post.comment.exception.CommentException;
import com.team.mystory.post.post.exception.PostException;
import com.team.mystory.security.jwt.exception.InvalidTokenException;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.expression.AccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.login.AccountException;

import static com.team.mystory.common.ResponseCode.INVALID_TOKEN;
import static com.team.mystory.common.ResponseCode.REQUEST_FAIL;

@RestControllerAdvice
public class GeneralExceptionHandler {
    @ExceptionHandler({InvalidTokenException.class})
    public ResponseEntity invalidTokenExceptionHandler(Exception e) {
        ResponseMessage message = ResponseMessage.of(INVALID_TOKEN , e.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
    }

    @ExceptionHandler({AccountException.class})
    public ResponseEntity accountExceptionHandler(Exception e) {
        ResponseMessage message = ResponseMessage.of(REQUEST_FAIL , e.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    }

    @ExceptionHandler({PostException.class , CommentException.class})
    public ResponseEntity postExceptionHandler(Exception e) {
        ResponseMessage message = ResponseMessage.of(REQUEST_FAIL , e.getMessage());

        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler({FileSizeLimitExceededException.class})
    public ResponseEntity fileUploadExceptionHandler(Exception e) {
        ResponseMessage message = ResponseMessage.of(REQUEST_FAIL , e.getMessage());

        return ResponseEntity.badRequest().body(message);
    }
}
