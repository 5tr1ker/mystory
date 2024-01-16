package com.team.mystory.admin.report.content.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.mystory.account.user.domain.QUser;
import com.team.mystory.admin.report.content.dto.ContentReportResponse;
import com.team.mystory.admin.report.content.dto.ReportDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.team.mystory.account.user.domain.QUser.user;
import static com.team.mystory.admin.report.content.entity.QContentReport.contentReport;
import static com.team.mystory.admin.report.content.entity.QReportData.reportData;

@RequiredArgsConstructor
public class ContentReportRepositoryImpl implements CustomContentReportRepository {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<ContentReportResponse> findAllContentReport(Pageable pageable) {
        QUser targetUser = new QUser("user2");

        return jpaQueryFactory.select(
                        Projections.constructor(
                                ContentReportResponse.class,
                                contentReport.contentReportId,
                                user.id,
                                contentReport.contentUrl,
                                contentReport.reportTime,
                                contentReport.content,
                                contentReport.isAction,
                                contentReport.reportType,
                                Projections.constructor(
                                        ReportDataResponse.class,
                                        reportData.reportDataId,
                                        targetUser.id,
                                        reportData.title,
                                        reportData.content
                                )
                        )
                )
                .from(contentReport)
                .innerJoin(contentReport.reporter, user)
                .innerJoin(contentReport.reportData, reportData)
                .innerJoin(reportData.target, targetUser)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(contentReport.reportTime.desc())
                .fetch();
    }
}
