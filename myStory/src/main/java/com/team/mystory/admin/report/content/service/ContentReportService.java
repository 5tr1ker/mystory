package com.team.mystory.admin.report.content.service;

import com.team.mystory.account.user.domain.User;
import com.team.mystory.account.user.repository.LoginRepository;
import com.team.mystory.admin.report.content.dto.ContentReportRequest;
import com.team.mystory.admin.report.content.dto.ContentReportResponse;
import com.team.mystory.admin.report.content.entity.ContentReport;
import com.team.mystory.admin.report.content.entity.ReportData;
import com.team.mystory.admin.report.content.exception.ContentReportException;
import com.team.mystory.admin.report.content.repository.ContentReportRepository;
import com.team.mystory.security.jwt.support.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentReportService {

    private final ContentReportRepository contentReportRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final LoginRepository loginRepository;

    @Transactional
    public void createNewContentReport(String accessToken, ContentReportRequest request) throws AccountException {
        String userId = jwtTokenProvider.getUserPk(accessToken);
        User user = loginRepository.findById(userId)
                .orElseThrow(() -> new AccountException("사용자를 찾을 수 없습니다."));

        ReportData reportData = createReportData(request);

        contentReportRepository.save(ContentReport.createContentReport(user, request, reportData));
    }

    public ReportData createReportData(ContentReportRequest request) throws AccountException {
        User targetUser =loginRepository.findById(request.getTarget().getWriter())
                .orElseThrow(() -> new AccountException("사용자를 찾을 수 없습니다."));

        return ReportData.createReportData(targetUser, request);
    }

    public List<ContentReportResponse> findAllContentReport(Pageable pageable) {
        return contentReportRepository.findAllContentReport(pageable);
    }

    public long findAllContentReportCount() {
        return contentReportRepository.countBy();
    }

    @Transactional
    public void toggleIsSolved(long reportId) {
        ContentReport report = contentReportRepository.findById(reportId)
                .orElseThrow(() -> new ContentReportException("해당 컨텐츠를 찾을 수 없습니다."));

        report.toggleIsAction();
    }

    @Transactional
    public void removeContentReport(long reportId) {
        contentReportRepository.deleteById(reportId);
    }

}
