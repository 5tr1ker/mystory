package com.team.mystory.admin.report.content.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.mystory.admin.report.content.dto.ContentReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import static com.team.mystory.account.user.domain.QUser.user;
import static com.team.mystory.admin.report.content.entity.QContentReport.contentReport;

import java.util.List;

@RequiredArgsConstructor
public class ContentReportRepositoryImpl implements CustomContentReportRepository {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<ContentReportResponse> findAllContentReport(Pageable pageable) {
        return jpaQueryFactory.select(
                        Projections.constructor(
                                ContentReportResponse.class,
                                contentReport.contentReportId,
                                user.id,
                                contentReport.reportTime,
                                contentReport.content,
                                contentReport.isAction
                        )
                )
                .from(contentReport)
                .innerJoin(contentReport.reporter, user)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
