package com.team.mystory.security.controller;

import com.team.mystory.common.ResponseMessage;
import com.team.mystory.security.jwt.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.team.mystory.common.ResponseCode.AUTHENTICATION_ERROR;
import static com.team.mystory.common.ResponseCode.AUTHORIZATION_ERROR;

@RestController
@RequiredArgsConstructor
public class SecurityController {

    final private JwtService jwtService;

    @GetMapping(value = "/authentication/denied")
    public ResponseEntity<ResponseMessage> informAuthenticationDenied(
            @CookieValue(name = "refreshToken", required = false) String authorization , HttpServletResponse response) {
        if (authorization == null) {
            ResponseMessage message = ResponseMessage.of(AUTHENTICATION_ERROR);

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
        }

        return ResponseEntity.ok().body(jwtService.validateRefreshToken(authorization , response));
    }

    @GetMapping(value = "/authorization/denied")
    public ResponseEntity<ResponseMessage> informAuthorizationDenied() {
        ResponseMessage message = ResponseMessage.of(AUTHORIZATION_ERROR);

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
    }

}
