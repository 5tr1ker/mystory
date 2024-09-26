package com.team.mystory.admin.visitant.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Visitant {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long visitantId;

    private String userIp;

    private String userAgent;

    private LocalDate visitDate;

}
