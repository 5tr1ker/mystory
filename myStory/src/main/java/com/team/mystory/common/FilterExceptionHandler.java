package com.team.mystory.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.mystory.oauth.exception.OAuth2EmailNotFoundException;
import com.team.mystory.oauth.support.OAuth2AuthenticationSuccessHandler;
import com.team.mystory.security.exception.TokenForgeryException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.team.mystory.common.ResponseCode.AUTHENTICATION_ERROR;

public class FilterExceptionHandler extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            filterChain.doFilter(request , response);
        } catch (TokenForgeryException e) {
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
