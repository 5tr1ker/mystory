package com.team.mystory.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.mystory.security.exception.TokenForgeryException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

import static com.team.mystory.common.ResponseCode.AUTHENTICATION_ERROR;
import static com.team.mystory.security.jwt.support.CookieSupport.deleteJwtTokenInCookie;

public class FilterExceptionHandler extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        try {
            chain.doFilter(request, response);
        } catch (UsernameNotFoundException e) {
            deleteJwtTokenInCookie((HttpServletResponse) response);

            setErrorResponse((HttpServletResponse) response , HttpServletResponse.SC_UNAUTHORIZED , "토큰이 변조되었거나 유효하지 않습니다.");
        } catch (TokenForgeryException e) {
            deleteJwtTokenInCookie((HttpServletResponse) response);

            setErrorResponse((HttpServletResponse) response , HttpServletResponse.SC_UNAUTHORIZED , "토큰이 만료되었거나 유효하지 않습니다.");
        } catch (ServletException e) {
            Throwable rootCause = findRootCause(e);
            if (rootCause != null) {
                setErrorResponse((HttpServletResponse) response , HttpStatus.BAD_REQUEST.value(), rootCause.getMessage());
            } else {
                setErrorResponse((HttpServletResponse) response , HttpStatus.BAD_REQUEST.value(), e.getMessage());
            }
        }
    }

    public Throwable findRootCause(Throwable throwable) {
        Throwable cause = throwable.getCause();
        if (cause == null) {
            return throwable;
        } else {
            return findRootCause(cause);
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

    public static void setSuccessResponse(HttpServletResponse response , ResponseCode responseCode) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(200);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ResponseMessage errorResponse = ResponseMessage.of(responseCode);

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

}
