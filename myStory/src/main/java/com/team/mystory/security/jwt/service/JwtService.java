package com.team.mystory.security.jwt.service;

import com.team.mystory.common.ResponseMessage;
import com.team.mystory.security.exception.TokenForgeryException;
import com.team.mystory.security.jwt.domain.RefreshToken;
import com.team.mystory.security.jwt.dto.Token;
import com.team.mystory.security.jwt.repository.RefreshTokenRepository;
import com.team.mystory.security.jwt.support.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static com.team.mystory.common.ResponseCode.CREATE_ACCESS_TOKEN;
import static com.team.mystory.security.jwt.domain.RefreshToken.creareRefreshToken;
import static com.team.mystory.security.jwt.support.CookieSupport.createAccessToken;
import static com.team.mystory.security.jwt.support.CookieSupport.deleteJwtTokenInCookie;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void login(Token token) {
        RefreshToken refreshToken = creareRefreshToken(token);
        String loginUserEmail = refreshToken.getKeyEmail();

        refreshTokenRepository.existsByKeyEmail(loginUserEmail).ifPresent(a -> {
            refreshTokenRepository.deleteByKeyEmail(loginUserEmail);
        });

        refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken getRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new TokenForgeryException("알 수 없는 RefreshToken 입니다."));
    }

    public ResponseMessage validateRefreshToken(String refreshToken , HttpServletResponse response) {
        try {
            RefreshToken token = getRefreshToken(refreshToken);
            String accessToken = jwtTokenProvider.validateRefreshToken(token);

            response.addCookie(createAccessToken(accessToken));

            return ResponseMessage.of(CREATE_ACCESS_TOKEN);
        } catch (NoSuchElementException e) {
            deleteJwtTokenInCookie(response);

            throw new TokenForgeryException("변조된 RefreshToken 입니다.");
        }
    }

}
