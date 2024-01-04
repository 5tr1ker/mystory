package com.team.mystory.admin.report.content.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ContentReportResponse {

    private long contentReportId;

    private String reporter;

    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:MM:SS" , timezone = "Asia/Seoul")
    private LocalDateTime reportTime;

    private String content;

    private boolean isAction;

    private String reportContentURL;

}
