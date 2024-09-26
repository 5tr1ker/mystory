package com.team.mystory.admin.report.content.entity;

import com.team.mystory.account.user.domain.User;
import com.team.mystory.admin.report.content.dto.ContentReportRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportData {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reportDataId;

    @ManyToOne(fetch = FetchType.LAZY)
    private User target;

    private String title;

    private String content;

    public static ReportData createReportData(User target, ContentReportRequest request) {
        return ReportData.builder()
                .target(target)
                .title(request.getTarget().getTitle())
                .content(request.getTarget().getContent())
                .build();
    }

}
