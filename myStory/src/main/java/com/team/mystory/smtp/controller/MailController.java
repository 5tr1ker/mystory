package com.team.mystory.smtp.controller;

import com.team.mystory.smtp.dto.CertRequest;
import com.team.mystory.smtp.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("/mail/send")
    public ResponseEntity sendMail(@RequestBody CertRequest request) {
        mailService.sendMail(request);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/mail/check")
    public ResponseEntity checkMail(@RequestBody CertRequest request) {
        mailService.checkVerificationCode(request);

        return ResponseEntity.ok().build();
    }

}
