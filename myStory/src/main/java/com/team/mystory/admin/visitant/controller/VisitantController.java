package com.team.mystory.admin.visitant.controller;

import com.team.mystory.admin.visitant.service.VisitantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/visitant")
public class VisitantController {

    private final VisitantService visitantService;

    @GetMapping("/count")
    public ResponseEntity findVisitantCountByVisitDate() {
        return ResponseEntity.ok(visitantService.findVisitantCountByVisitDate());
    }

}
