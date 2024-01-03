package com.team.mystory.admin.report.bug.entity;

import com.team.mystory.account.user.domain.User;
import com.team.mystory.common.config.BooleanConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BugReport {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bugReportId;

    @ManyToOne(fetch = FetchType.LAZY)
    private User reporter;

    @CreationTimestamp
    private LocalDateTime reportTime;

    @Column(nullable = false)
    private String content;

    @Convert(converter = BooleanConverter.class)
    private boolean isSolved = false;

    public static BugReport createBugReport(User user, String content) {
        return BugReport.builder()
                .reporter(user)
                .content(content)
                .isSolved(false)
                .build();
    }

    public void toggleSolved() {
        isSolved = isSolved ? false : true;
    }

}