package com.team.mystory.admin.report.content.controller;

import com.team.mystory.admin.report.content.dto.ContentReportRequest;
import com.team.mystory.admin.report.content.service.ContentReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/report/content")
public class ContentReportController {

    private final ContentReportService contentReportService;

    @PostMapping
    public ResponseEntity createNewContentReport(@CookieValue String accessToken, @RequestBody ContentReportRequest request)
            throws AccountException {
        contentReportService.createNewContentReport(accessToken, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity findAllContentReport(Pageable pageable) {
        return ResponseEntity.ok(contentReportService.findAllContentReport(pageable));
    }

    @GetMapping("/count")
    public ResponseEntity findAllContentReportCount() {
        return ResponseEntity.ok(contentReportService.findAllContentReportCount());
    }

    @PatchMapping("/{reportId}")
    public ResponseEntity toggleIsSolved(@PathVariable long reportId) {
        contentReportService.toggleIsSolved(reportId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{reportId}")
    public ResponseEntity removeContentReport(@PathVariable long reportId) {
        contentReportService.removeContentReport(reportId);

        return ResponseEntity.noContent().build();
    }

}
