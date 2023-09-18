package com.team.mystory.post.comment.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.mystory.account.user.domain.QUser;
import com.team.mystory.post.comment.domain.Comment;
import com.team.mystory.post.comment.dto.CommentResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.team.mystory.account.user.domain.QUser.user;
import static com.team.mystory.post.comment.domain.QComment.comment;
import static com.team.mystory.post.post.domain.QPost.post;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CustomCommentRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Comment> findCommentByCommentIdAndUserId(long commentId, String userId) {
        Comment result = queryFactory.select(comment)
                .from(comment)
                .innerJoin(comment.writer , user).on(user.id.eq(userId))
                .where(comment.commentId.eq(commentId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public List<CommentResponse> findCommentByPostId(long postId) {
        return queryFactory.select(Projections.constructor(CommentResponse.class ,
                       post.postId , comment.commentId , comment.content , comment.postDate , user.id , user.profileImage))
                .from(comment)
                .innerJoin(comment.writer , user)
                .innerJoin(comment.post , post).on(post.postId.eq(postId))
                .fetch();
    }

    @Override
    public List<CommentResponse> findCommentByCommentPostWithoutMe(String userId) {
        QUser subUser = new QUser("subUser");

        return queryFactory.select(Projections.constructor(CommentResponse.class ,
                       post.postId , comment.commentId , comment.content , comment.postDate , user.id , user.profileImage))
                .from(comment)
                .innerJoin(comment.writer , user).on(user.id.ne(userId))
                .innerJoin(comment.post , post)
                .on(post.eqAny(JPAExpressions.select(post)
                        .from(post)
                        .innerJoin(post.writer , subUser).on(subUser.id.eq(userId))
                ))
                .orderBy(comment.commentId.desc())
                .limit(10)
                .fetch();
    }
}
