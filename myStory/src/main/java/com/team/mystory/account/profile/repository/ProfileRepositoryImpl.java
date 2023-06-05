package com.team.mystory.account.profile.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.mystory.account.profile.domain.Profile;
import com.team.mystory.account.profile.dto.StatisticsResponse;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.Optional;

import static com.team.mystory.account.profile.domain.QProfile.profile;
import static com.team.mystory.account.user.domain.QUser.user;
import static com.team.mystory.post.comment.domain.QComment.comment;
import static com.team.mystory.post.post.domain.QPost.post;

@RequiredArgsConstructor
public class ProfileRepositoryImpl implements CustomProfileRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Profile> findProfileByUserId(String userId) {
        Profile result = queryFactory.select(profile)
                .from(user)
                .innerJoin(user.profile)
                .where(user.id.eq(userId)).fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public StatisticsResponse getStatisticsOfUser(String userId) {

        long totalPost = queryFactory.select(post.count())
                .from(post)
                .innerJoin(post.writer , user).on(user.id.eq(userId))
                .fetchOne();

        Integer totalView = queryFactory.select(post.views.sum())
                .from(post)
                .innerJoin(post.writer , user).on(user.id.eq(userId)).fetchOne();

        if(totalView == null) {
            totalView = 0;
        }

        long totalComment = queryFactory.select(comment.count())
                .from(comment)
                .innerJoin(comment.writer , user).on(user.id.eq(userId)).fetchOne();

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
