package com.team.mystory.admin.visitant.repository;

import com.team.mystory.admin.visitant.dto.VisitantResponse;

import java.util.List;

public interface CustomVisitantRepository {

    List<VisitantResponse> findVisitantCountByVisitDate();

}
