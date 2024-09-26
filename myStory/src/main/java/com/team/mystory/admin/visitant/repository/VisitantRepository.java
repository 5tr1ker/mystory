package com.team.mystory.admin.visitant.repository;

import com.team.mystory.admin.visitant.domain.Visitant;
import com.team.mystory.admin.visitant.dto.VisitantResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VisitantRepository extends JpaRepository<Visitant, Long>, CustomVisitantRepository {

    boolean existsByUserIpAndVisitDate(String userIp, LocalDate visitDate);

}
