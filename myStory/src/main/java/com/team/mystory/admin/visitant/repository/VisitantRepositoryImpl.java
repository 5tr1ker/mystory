package com.team.mystory.admin.visitant.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.mystory.admin.visitant.dto.VisitantResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.team.mystory.admin.visitant.domain.QVisitant.visitant;
import static com.team.mystory.account.user.domain.QUser.user;

@RequiredArgsConstructor
public class VisitantRepositoryImpl implements CustomVisitantRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<VisitantResponse> findVisitantCountByVisitDate() {
        return queryFactory.select(Projections.constructor(VisitantResponse.class
                        , visitant.count(), visitant.visitDate ))
                .from(visitant)
                .groupBy(visitant.visitDate)
                .orderBy(visitant.visitantId.desc())
                .limit(20L)
                .fetch();
    }
}
