package com.team.mystory.security.controller;

import com.team.mystory.common.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.team.mystory.common.ResponseCode.AUTHORIZATION_ERROR;

@RestController
@RequiredArgsConstructor
public class SecurityController {

    @GetMapping(value = "/authorization/denied")
    public ResponseEntity<ResponseMessage> informAuthorizationDenied() {
        ResponseMessage message = ResponseMessage.of(AUTHORIZATION_ERROR);

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
    }

}
