package com.team.mystory.admin.visitant.service;

import com.team.mystory.admin.visitant.dto.VisitantResponse;
import com.team.mystory.admin.visitant.repository.VisitantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitantService {

    private final VisitantRepository visitantRepository;

    public List<VisitantResponse> findVisitantCountByVisitDate() {
        return visitantRepository.findVisitantCountByVisitDate();
    }
}
