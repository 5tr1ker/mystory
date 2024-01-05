package com.team.mystory.smtp.service;

import com.team.mystory.smtp.dto.CertRequest;
import com.team.mystory.smtp.entity.MailCert;
import com.team.mystory.smtp.exception.MailException;
import com.team.mystory.smtp.repository.MailCertRepository;
import com.team.mystory.smtp.util.MailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MailService {

    private final MailUtil mailUtil;
    private final MailCertRepository mailCertRepository;

    @Transactional
    public void sendMail(CertRequest request) {
        createVerification(request.getEmail());

        if (!mailUtil.sendMail(request.getEmail())) {
            throw new MailException("메일을 전송하는 도중에 오류가 발생했습니다.");
        }
    }

    private MailCert createVerification(String id) {
        String code = createVerificationCode();

        MailCert mailCert = updateOrSaveVerificationCode(id, code);

        return mailCertRepository.save(mailCert);
    }

    private MailCert updateOrSaveVerificationCode(String id, String code) {
        MailCert mailCert = mailCertRepository.findById(id)
                .orElseGet(() -> MailCert.createMailCert(id, code));

        return mailCert;
    }

    @Transactional
    public void checkVerificationCode(CertRequest request) {
        if(isCorrectVerificationCode(request)) {
            mailCertRepository.deleteById(request.getEmail());
        }
    }

    private boolean isCorrectVerificationCode(CertRequest request) {
        MailCert mailCert = mailCertRepository.findById(request.getEmail())
                .orElseThrow(() -> new MailException("잘못된 접근입니다."));

        if(!mailCert.isCorrectVerificationCode(request.getCode())) {
            throw new MailException("잘못된 코드입니다.");
        };

        return true;
    }

    private String createVerificationCode() {
        String code = UUID.randomUUID().toString();

        return code;
    }

}