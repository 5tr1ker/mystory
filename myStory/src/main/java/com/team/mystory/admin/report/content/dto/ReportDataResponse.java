package com.team.mystory.admin.report.content.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReportDataResponse {

    private long reportDataId;

    private String targetId;

    private String title;

    private String content;

}