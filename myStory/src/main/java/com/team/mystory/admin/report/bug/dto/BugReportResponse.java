package com.team.mystory.admin.report.bug.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team.mystory.account.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class BugReportResponse {

    private long bugReportId;

    private String reporter;

    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:MM:SS" , timezone = "Asia/Seoul")
    private LocalDateTime reportTime;

    private String content;

    private boolean isSolved;

}
