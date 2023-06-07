package com.team.mystory.security.controller;

import com.team.mystory.common.ResponseMessage;
import com.team.mystory.security.service.SecurityService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.team.mystory.common.ResponseCode.AUTHORIZATION_ERROR;
import static com.team.mystory.common.ResponseCode.LOGIN_SUCCESS;

@RestController
@RequiredArgsConstructor
public class SecurityController {

    private final SecurityService securityService;

    @GetMapping(value = "/authorization/denied")
    public ResponseEntity<ResponseMessage> informAuthorizationDenied() {
        ResponseMessage message = ResponseMessage.of(AUTHORIZATION_ERROR);

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
    }

    @PostMapping(value = "/oauth/token")
    public ResponseEntity<ResponseMessage> conversionJwtToken(@RequestParam String name , HttpServletResponse response) {
        securityService.createJwtTokenByName(name , response);

        return ResponseEntity.ok().body(ResponseMessage.of(LOGIN_SUCCESS));
    }

}
