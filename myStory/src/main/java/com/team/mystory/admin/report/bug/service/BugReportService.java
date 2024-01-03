package com.team.mystory.admin.report.bug.service;

import com.team.mystory.account.user.domain.User;
import com.team.mystory.account.user.repository.LoginRepository;
import com.team.mystory.admin.report.bug.dto.BugReportRequest;
import com.team.mystory.admin.report.bug.dto.BugReportResponse;
import com.team.mystory.admin.report.bug.entity.BugReport;
import com.team.mystory.admin.report.bug.exception.BugReportException;
import com.team.mystory.admin.report.bug.repository.BugReportRepository;
import com.team.mystory.security.jwt.support.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BugReportService {

    private final JwtTokenProvider jwtTokenProvider;
    private final LoginRepository loginRepository;
    private final BugReportRepository bugReportRepository;

    public void createNewBugReport(String accessToken, BugReportRequest request) throws AccountException {
        String userId = jwtTokenProvider.getUserPk(accessToken);
        User user = loginRepository.findById(userId)
                .orElseThrow(() -> new AccountException("사용자를 찾을 수 없습니다."));

        bugReportRepository.save(BugReport.createBugReport(user, request.getContent()));
    }

    public List<BugReportResponse> findAllBugReport(Pageable pageable) {
        return bugReportRepository.findAllBugReport(pageable);
    }

    public long findAllBugReportCount() {
        return bugReportRepository.countBy();
    }

    @Transactional
    public void modifySolvedInBugReport(long reportId) {
        BugReport bugReport = bugReportRepository.findByBugReportId(reportId)
                .orElseThrow(() -> new BugReportException("찾을 수 없는 컨텐츠입니다."));

        bugReport.toggleSolved();
    }

    @Transactional
    public void removeBugReport(long reportId) {
        bugReportRepository.deleteById(reportId);
    }

}
