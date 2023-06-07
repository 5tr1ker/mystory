package com.team.mystory.security.jwt.support;

import com.team.mystory.security.jwt.dto.Token;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class CookieSupport {

    @Value("${server.url}")
    private static String DOMAIN_URL;

    public static Cookie createAccessToken(String access) {
        Cookie accessToken = new Cookie("accessToken", access);
        accessToken.setPath("/");
        accessToken.setMaxAge(30 * 60 * 1000);
        accessToken.setSecure(true);
        accessToken.setDomain(DOMAIN_URL);
        accessToken.setHttpOnly(true);

        return accessToken;
    }

    public static Cookie createRefreshToken(String refresh) {
        Cookie refreshToken = new Cookie("refreshToken", refresh);
        refreshToken.setPath("/");
        refreshToken.setMaxAge(14 * 24 * 60 * 60 * 1000);
        refreshToken.setSecure(true);
        refreshToken.setDomain(DOMAIN_URL);
        refreshToken.setHttpOnly(true);

        return refreshToken;
    }

    public static void setCookieFromJwt(Token token , HttpServletResponse response) {
        response.addCookie(createAccessToken(token.getAccessToken()));
        response.addCookie(createRefreshToken(token.getRefreshToken()));
    }

    public static void deleteJwtTokenInCookie(HttpServletResponse response) {
        Cookie accessToken = new Cookie("accessToken", null);
        accessToken.setPath("/");
        accessToken.setMaxAge(0);
        accessToken.setDomain(DOMAIN_URL);

        Cookie refreshToken = new Cookie("refreshToken", null);
        refreshToken.setPath("/");
        refreshToken.setMaxAge(0);
        refreshToken.setDomain(DOMAIN_URL);

        response.addCookie(accessToken);
        response.addCookie(refreshToken);
    }

}
