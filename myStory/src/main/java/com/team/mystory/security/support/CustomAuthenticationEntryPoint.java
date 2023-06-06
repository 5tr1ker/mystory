package com.team.mystory.security.support;

import com.team.mystory.security.jwt.service.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.team.mystory.common.FilterExceptionHandler.setErrorResponse;
import static com.team.mystory.common.ResponseCode.CREATE_ACCESS_TOKEN;

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
        }
    }
}
