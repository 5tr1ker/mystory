package com.team.mystory.admin.report.bug.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.mystory.admin.report.bug.dto.BugReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import static com.team.mystory.account.user.domain.QUser.user;
import static com.team.mystory.admin.report.bug.entity.QBugReport.bugReport;

import java.util.List;

@RequiredArgsConstructor
public class BugReportRepositoryImpl implements CustomBugReportRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<BugReportResponse> findAllBugReport(Pageable pageable) {
        return jpaQueryFactory.select(
                        Projections.constructor(BugReportResponse.class,
                                bugReport.bugReportId,
                                user.id,
                                bugReport.reportTime,
                                bugReport.content,
                                bugReport.isSolved
                        )
                ).from(bugReport)
                .innerJoin(bugReport.reporter , user)
                .orderBy(bugReport.bugReportId.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }
}
