package com.team.mystory.admin.visitant.repository;

import com.team.mystory.admin.visitant.domain.Visitant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface VisitantRepository extends JpaRepository<Visitant, Long> {

    boolean existsByUserIpAndVisitDate(String userIp, LocalDate visitDate);

}
