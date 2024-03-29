package com.team.mystory.common.exception;

import com.team.mystory.account.user.exception.LoginException;
import com.team.mystory.common.response.ResponseMessage;
import com.team.mystory.meeting.meeting.exception.MeetingException;
import com.team.mystory.meeting.reservation.exception.ReservationException;
import com.team.mystory.post.comment.exception.CommentException;
import com.team.mystory.post.post.exception.PostException;
import com.team.mystory.security.jwt.exception.InvalidTokenException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;

import static com.team.mystory.common.response.ResponseCode.*;
import static com.team.mystory.security.jwt.support.CookieSupport.deleteJwtTokenInCookie;

@RestControllerAdvice
public class GeneralExceptionHandler {
    @ExceptionHandler({InvalidTokenException.class , AuthenticationException.class})
    public ResponseEntity invalidTokenExceptionHandler(Exception e) {
        ResponseMessage message = ResponseMessage.of(INVALID_TOKEN , e.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
    }

    @ExceptionHandler({LoginException.class})
    public ResponseEntity accountExceptionHandler(Exception e) {
        ResponseMessage message = ResponseMessage.of(REQUEST_FAIL , e.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    }

    @ExceptionHandler({PostException.class , CommentException.class})
    public ResponseEntity postExceptionHandler(Exception e) {
        ResponseMessage message = ResponseMessage.of(REQUEST_FAIL , e.getMessage());

        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler({MeetingException.class , ReservationException.class})
    public ResponseEntity meetingExceptionHandler(Exception e) {
        ResponseMessage message = ResponseMessage.of(REQUEST_FAIL , e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler({FileSizeLimitExceededException.class})
    public ResponseEntity fileUploadExceptionHandler(Exception e) {
        ResponseMessage message = ResponseMessage.of(REQUEST_FAIL , e.getMessage());

        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler({MalformedJwtException.class})
    public ResponseEntity malformedJwtExceptionHandler(Exception e , HttpServletResponse response) {
        ResponseMessage message = ResponseMessage.of(AUTHORIZATION_ERROR , "변조된 RefreshToken 입니다.");

        deleteJwtTokenInCookie(response);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    }
}
