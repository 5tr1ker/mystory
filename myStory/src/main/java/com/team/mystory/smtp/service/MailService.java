package com.team.mystory.smtp.service;

import com.team.mystory.account.user.domain.User;
import com.team.mystory.account.user.repository.LoginRepository;
import com.team.mystory.smtp.dto.CertRequest;
import com.team.mystory.smtp.entity.MailCert;
import com.team.mystory.smtp.exception.MailException;
import com.team.mystory.smtp.repository.MailCertRepository;
import com.team.mystory.smtp.util.MailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.team.mystory.common.response.message.AccountMessage.NOT_FOUNT_ACCOUNT;
import static com.team.mystory.common.response.message.MailMessage.*;

@Service
@RequiredArgsConstructor
public class MailService {

    private final MailUtil mailUtil;
    private final MailCertRepository mailCertRepository;
    private final LoginRepository loginRepository;

    @Transactional
    public void sendMail(CertRequest request) {
        User user = findUserByEmail(request.getEmail());
        MailCert mailCert = createVerification(request.getEmail());

        if (!mailUtil.sendMail(user.getId() ,request.getEmail(), mailCert.getVerificationCode())) {
            throw new MailException(SMTP_SERVER_ERROR);
        }
    }

    private User findUserByEmail(String email) {
        return loginRepository.findByEmail(email)
                .orElseThrow(() -> new MailException(NOT_FOUNT_ACCOUNT));
    }

    private MailCert createVerification(String id) {
        String code = createVerificationCode();

        MailCert mailCert = createVerificationCode(id, code);

        return mailCertRepository.save(mailCert);
    }

    private MailCert createVerificationCode(String id, String code) {
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
                .orElseThrow(() -> new MailException(UNUSUAL_APPROACH));

        if(!mailCert.isCorrectVerificationCode(request.getCode())) {
            throw new MailException(NOT_MATCHED_CODE);
        }

        return true;
    }

    private String createVerificationCode() {
        String code = UUID.randomUUID().toString();

        return code;
    }

}