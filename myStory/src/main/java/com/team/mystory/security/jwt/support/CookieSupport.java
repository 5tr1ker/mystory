package com.team.mystory.security.jwt.support;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseCookie;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class CookieSupport {

    private static final String DOMAIN_URL = "localhost";

    public static ResponseCookie createResponseAccessToken(String access) {
        return ResponseCookie.from("accessToken", access)
                .httpOnly(true)
                //.secure(true)
                .path("/")
                //.sameSite("None")
                .maxAge(30 * 60 * 1000L)
                .domain(DOMAIN_URL)
                .build();
    }

    public static ResponseCookie createResponseRefreshToken(String refresh) {
        return ResponseCookie.from("refreshToken", refresh)
                .httpOnly(true)
                //.secure(true)
                .path("/")
                //.sameSite("None")
                .maxAge(14 * 24 * 60 * 60 * 1000L)
                .domain(DOMAIN_URL)
                .build();
    }

    public static Cookie createAccessToken(String access) {
        Cookie accessToken = new Cookie("accessToken", access);
        accessToken.setPath("/");
        accessToken.setMaxAge(30 * 60 * 1000);
        //accessToken.setSecure(true);
        accessToken.setDomain(DOMAIN_URL);
        accessToken.setHttpOnly(true);

        return accessToken;
    }

    public static Cookie createRefreshToken(String refresh) {
        Cookie refreshToken = new Cookie("refreshToken", refresh);
        refreshToken.setPath("/");
        refreshToken.setMaxAge(14 * 24 * 60 * 60 * 1000);
        //refreshToken.setSecure(true);
        refreshToken.setDomain(DOMAIN_URL);
        refreshToken.setHttpOnly(true);

        return refreshToken;
    }

    public static void deleteJwtTokenInCookie(HttpServletResponse response) {
        Cookie accessToken = new Cookie("accessToken", null);
        accessToken.setPath("/");
        accessToken.setMaxAge(0);
        //accessToken.setSecure(true);
        accessToken.setDomain(DOMAIN_URL);
        accessToken.setHttpOnly(true);

        Cookie refreshToken = new Cookie("refreshToken", null);
        refreshToken.setPath("/");
        refreshToken.setMaxAge(0);
        //refreshToken.setSecure(true);
        refreshToken.setDomain(DOMAIN_URL);
        refreshToken.setHttpOnly(true);

        response.addCookie(accessToken);
        response.addCookie(refreshToken);
    }

}
