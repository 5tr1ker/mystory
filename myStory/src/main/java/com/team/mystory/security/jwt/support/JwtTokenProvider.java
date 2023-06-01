package com.team.mystory.security.jwt.support;

import com.team.mystory.security.jwt.domain.RefreshToken;
import com.team.mystory.security.jwt.dto.Token;
import com.team.mystory.security.jwt.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final UserDetailsService loginService;

    private String secretKey = "mystorySecretKey";
    private String refreshKey = "mystorySecretKey";
    private long tokenValidTime = 30 * 60 * 1000L;
    private long refreshTokenValidTime = 14 * 24 * 60 * 60 * 1000L;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        refreshKey = Base64.getEncoder().encodeToString(refreshKey.getBytes());
    }

    public Token createAccessToken(String userPk, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);
        
        String accessToken = createAccessToken(claims);
        String refreshToken = createRefreshToken(claims);

        return Token.builder().accessToken(accessToken).refreshToken(refreshToken).key(userPk).build();
    }

    public String validateRefreshToken(RefreshToken refreshTokenObj){
        String refreshToken = refreshTokenObj.getRefreshToken();
        Jws<Claims> claims = Jwts.parser().setSigningKey(refreshKey).parseClaimsJws(refreshToken);

        if (!claims.getBody().getExpiration().before(new Date())) {
            return recreationAccessToken(claims.getBody().get("sub").toString(), claims.getBody().get("roles"));
        }

        throw new InvalidTokenException("Refresh Token 이 만료되었거나 위조되었습니다.");
    }

    public String recreationAccessToken(String userEmail, Object roles){
        Claims claims = Jwts.claims().setSubject(userEmail);
        claims.put("roles", roles);

        return createAccessToken(claims);
    }
    

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = loginService.loadUserByUsername(this.getUserPk(token));
        if(userDetails == null) return null;
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String createAccessToken(Claims claims) {
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String createRefreshToken(Claims claims) {
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, refreshKey)
                .compact();
    }
}
