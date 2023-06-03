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

    // @Query("SELECT c FROM Post p join p.freeCommit c where p.postId = :postNumber")

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
        return queryFactory.select(Projections.constructor(CommentResponse.class
                        , comment.commentId , comment.content , comment.postDate , user.id))
                .from(post)
                .innerJoin(post.writer , user)
                .innerJoin(post.comment , comment)
                .where(post.postId.eq(postId))
                .fetch();
    }

    // @Query(value = "select c from Comment c where c.writer != :idStatus and c.post =
    // any (select p from Post p where p.writer = :idStatus) order by c.id DESC")

    @Override
    public List<CommentResponse> findCommentFromRegisteredPostByUserId(String userId) {
        QUser subUser = new QUser("subUser");

        return queryFactory.select(Projections.constructor(CommentResponse.class
                        , comment.commentId , comment.content , comment.postDate , user.id))
                .from(comment)
                .innerJoin(comment.writer , user).on(user.id.ne(userId))
                .where(comment.post.eqAny(JPAExpressions.select(post)
                        .from(post)
                        .innerJoin(post.writer , subUser).on(subUser.id.eq(userId))
                ))
                .orderBy(comment.commentId.desc())
                .fetch();
    }
}
