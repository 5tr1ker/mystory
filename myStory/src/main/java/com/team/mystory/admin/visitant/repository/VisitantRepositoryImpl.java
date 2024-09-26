package com.team.mystory.admin.visitant.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.mystory.admin.visitant.dto.VisitantResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.team.mystory.admin.visitant.domain.QVisitant.visitant;

@RequiredArgsConstructor
public class VisitantRepositoryImpl implements CustomVisitantRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<VisitantResponse> findVisitantCountByVisitDate() {
        return queryFactory.select(Projections.constructor(VisitantResponse.class
                        , visitant.count(), visitant.visitDate ))
                .from(visitant)
                .groupBy(visitant.visitDate)
                .orderBy(visitant.visitDate.asc())
                .limit(20L)
                .fetch();
    }
}
