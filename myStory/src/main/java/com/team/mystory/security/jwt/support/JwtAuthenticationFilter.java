package com.team.mystory.security.jwt.support;

import com.team.mystory.security.jwt.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Arrays;

import static com.team.mystory.common.exception.FilterExceptionHandler.setSuccessResponse;
import static com.team.mystory.common.response.ResponseCode.CREATE_ACCESS_TOKEN;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtService jwtService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = getAccessTokenFromHeader(request);

        if (token != null && jwtTokenProvider.validateAccessToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else if (token != null) {
            jwtService.validateRefreshToken((HttpServletRequest) request , (HttpServletResponse) response);

            setSuccessResponse(((HttpServletResponse) response) , CREATE_ACCESS_TOKEN);
            return;
        }

        chain.doFilter(request, response);
    }

    public String getAccessTokenFromHeader(ServletRequest request) {
        Cookie cookies[] = ((HttpServletRequest) request).getCookies();

        if (cookies != null && cookies.length != 0) {
            return Arrays.stream(cookies)
                    .filter(c -> c.getName().equals("accessToken")).findFirst().map(Cookie::getValue)
                    .orElse(null);
        }

        return null;
    }
}
