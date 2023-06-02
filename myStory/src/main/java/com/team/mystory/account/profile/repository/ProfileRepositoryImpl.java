package com.team.mystory.account.profile.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.mystory.account.profile.domain.ProfileSetting;
import com.team.mystory.account.profile.dto.StatisticsResponse;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.Optional;

import static com.team.mystory.account.profile.domain.QProfileSetting.profileSetting;
import static com.team.mystory.account.user.domain.QUser.user;
import static com.team.mystory.post.comment.domain.QFreeCommit.freeCommit;
import static com.team.mystory.post.post.domain.QPost.post;

@RequiredArgsConstructor
public class ProfileRepositoryImpl implements CustomProfileRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<ProfileSetting> findProfileByUserId(String userId) {
        ProfileSetting result = queryFactory.select(profileSetting)
                .from(user)
                .innerJoin(user.profileSetting)
                .where(user.id.eq(userId)).fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public StatisticsResponse getStatisticsOfUser(String userId) {

        long totalPost = queryFactory.select(post.count())
                .from(post)
                .innerJoin(post.writer).on(user.id.eq(userId)).fetchOne();

        Integer totalView = queryFactory.select(post.views.sum())
                .from(post)
                .innerJoin(post.writer).on(user.id.eq(userId)).fetchOne();

        if(totalView == null) {
            totalView = 0;
        }

        long totalComment = queryFactory.select(freeCommit.count())
                .from(freeCommit)
                .innerJoin(freeCommit.user).on(user.id.eq(userId)).fetchOne();

        Date joinDate = queryFactory.select(user.joinDate)
                .from(user)
                .where(user.id.eq(userId)).fetchOne();

        return StatisticsResponse.builder()
                .totalPost(totalPost)
                .totalPostView(totalView)
                .totalComment(totalComment)
                .joinData(joinDate.toString())
                .build();
    }
}
