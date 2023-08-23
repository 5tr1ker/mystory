package com.team.mystory.account.user.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.mystory.account.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.team.mystory.account.profile.domain.QProfile.profile;
import static com.team.mystory.account.user.domain.QUser.user;

@RequiredArgsConstructor
public class LoginRepositoryImpl implements CustomLoginRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<UserResponse> findUserResponseById(String id) {
        UserResponse result = queryFactory.select(Projections.constructor(UserResponse.class
                        , user.userKey , user.id , user.password , user.joinDate , user.profileImage , profile.options))
                .from(user)
                .innerJoin(user.profile , profile).on(profile.eq(profile))
                .where(user.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
