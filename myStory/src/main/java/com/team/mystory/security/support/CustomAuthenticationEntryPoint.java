package com.team.mystory.security.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.mystory.common.ResponseMessage;
import com.team.mystory.security.exception.TokenForgeryException;
import com.team.mystory.security.jwt.service.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.team.mystory.common.ResponseCode.AUTHENTICATION_ERROR;
import static com.team.mystory.common.ResponseCode.CREATE_ACCESS_TOKEN;
import static com.team.mystory.security.jwt.support.CookieSupport.deleteJwtTokenInCookie;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final JwtService jwtService;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {

        try {
            jwtService.validateRefreshToken(request , response);

            setErrorResponse(response , HttpServletResponse.SC_OK , CREATE_ACCESS_TOKEN.getMessage());
        } catch (javax.naming.AuthenticationException e) {
            setErrorResponse(response , HttpServletResponse.SC_UNAUTHORIZED , "인증되지 않은 사용자입니다.");
        } catch (TokenForgeryException e) {
            deleteJwtTokenInCookie(response);

            setErrorResponse(response , HttpStatus.UNAUTHORIZED.value() , e.getMessage());
        }
    }

    public static void setErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(status);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ResponseMessage errorResponse = ResponseMessage.of(AUTHENTICATION_ERROR , message);

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

}
