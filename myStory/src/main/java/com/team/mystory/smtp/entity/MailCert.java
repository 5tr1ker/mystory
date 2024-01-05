package com.team.mystory.smtp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MailCert {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long mailCertId;

    private String id;

    private String verificationCode;

    public boolean isCorrectVerificationCode(String code) {
        return verificationCode.equals(code);
    }

    public static MailCert createMailCert(String id, String code) {
        return MailCert.builder()
                .id(id)
                .verificationCode(code)
                .build();
    }

}
