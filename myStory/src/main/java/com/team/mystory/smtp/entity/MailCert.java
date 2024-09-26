package com.team.mystory.smtp.entity;

import jakarta.persistence.*;
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

    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
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
