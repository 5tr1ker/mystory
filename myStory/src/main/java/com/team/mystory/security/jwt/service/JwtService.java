package com.team.mystory.security.jwt.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.team.mystory.common.ResponseCode;
import com.team.mystory.common.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team.mystory.security.jwt.support.JwtTokenProvider;
import com.team.mystory.security.jwt.dto.Token;
import com.team.mystory.security.jwt.repository.RefreshTokenRepository;
import com.team.mystory.security.jwt.domain.RefreshToken;

import static com.team.mystory.common.ResponseCode.CREATE_ACCESS_TOKEN;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void login(Token token) {
        RefreshToken refreshToken = RefreshToken.builder()
                .keyEmail(token.getKey())
                .refreshToken(token.getRefreshToken())
                .build();

        String loginUserEmail = refreshToken.getKeyEmail();

        refreshTokenRepository.existsByKeyEmail(loginUserEmail).ifPresent(a -> {
            refreshTokenRepository.deleteByKeyEmail(loginUserEmail);
        });

        refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> getRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken);
    }

    public ResponseMessage validateRefreshToken(String refreshToken) {
        RefreshToken refreshToken1 = getRefreshToken(refreshToken).get();
        String createdAccessToken = jwtTokenProvider.validateRefreshToken(refreshToken1);

        return ResponseMessage.of(CREATE_ACCESS_TOKEN , createdAccessToken);
    }
}
