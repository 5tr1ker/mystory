package com.team.mystory.admin.report.bug.controller;

import com.team.mystory.admin.report.bug.dto.BugReportRequest;
import com.team.mystory.admin.report.bug.service.BugReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/report/bug")
public class BugReportController {

    private final BugReportService bugReportService;

    @PostMapping
    public ResponseEntity createNewBugReport(@CookieValue String accessToken, @RequestBody BugReportRequest request)
            throws AccountException {
        bugReportService.createNewBugReport(accessToken, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity findAllBugReport(Pageable pageable) {
        return ResponseEntity.ok().body(bugReportService.findAllBugReport(pageable));
    }

    @PatchMapping("/{reportId}")
    public ResponseEntity modifySolvedInBugReport(@PathVariable long reportId) {
        bugReportService.modifySolvedInBugReport(reportId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{reportId}")
    public ResponseEntity removeBugReport(@PathVariable long reportId) {
        bugReportService.removeBugReport(reportId);

        return ResponseEntity.noContent().build();
    }

}
