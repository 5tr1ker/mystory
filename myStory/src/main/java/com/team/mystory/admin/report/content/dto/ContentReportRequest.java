package com.team.mystory.admin.report.content.dto;

import com.team.mystory.admin.report.content.entity.ReportType;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ContentReportRequest {

    private String content;

    private String reportContentURL;

    private ReportType reportType;

    private ReportTarget target;

    @Getter
    @ToString
    public class ReportTarget {

        private String writer;

        private String title;

        private String content;

    }

}
