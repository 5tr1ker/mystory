package com.team.mystory.security.service;

import com.team.mystory.account.user.constant.UserRole;
import com.team.mystory.security.jwt.dto.Token;
import com.team.mystory.security.jwt.support.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.team.mystory.security.jwt.support.CookieSupport.setCookieFromJwt;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final JwtTokenProvider jwtTokenProvider;

    public void createJwtTokenByName(String name, HttpServletResponse response) {
        Token token = jwtTokenProvider.createJwtToken(name , UserRole.USER);

        setCookieFromJwt(token , response);
    }
}
