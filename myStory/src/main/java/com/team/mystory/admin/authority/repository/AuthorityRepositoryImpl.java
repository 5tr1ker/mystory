package com.team.mystory.admin.authority.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.mystory.admin.authority.dto.AuthorityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import static com.team.mystory.account.user.domain.QUser.user;

import java.util.List;

@RequiredArgsConstructor
public class AuthorityRepositoryImpl implements CustomAuthorityRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<AuthorityResponse> findAllAuthorityUser(Pageable pageable) {
        return jpaQueryFactory.select(Projections.constructor(AuthorityResponse.class,
                        user.userKey,
                        user.id,
                        user.joinDate,
                        user.lastLoginDate,
                        user.suspensionReason,
                        user.suspensionDate,
                        user.isSuspension,
                        user.role
                ))
                .from(user)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<AuthorityResponse> findAllAuthorityUserById(Pageable pageable, String search) {
        return jpaQueryFactory.select(Projections.constructor(AuthorityResponse.class,
                        user.userKey,
                        user.id,
                        user.joinDate,
                        user.lastLoginDate,
                        user.suspensionReason,
                        user.suspensionDate,
                        user.isSuspension,
                        user.role
                ))
                .from(user)
                .where(user.id.contains(search))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
