package com.team.mystory.post.comment.repository;

import com.team.mystory.post.comment.domain.Comment;
import com.team.mystory.post.comment.dto.CommentResponse;

import java.util.List;
import java.util.Optional;

public interface CustomCommentRepository {

    Optional<Comment> findCommentByCommentIdAndUserId(long commentId , String userId);

    List<CommentResponse> findCommentByPostId(long postId);

    List<CommentResponse> findCommentByCommentPostWithoutMe(String userId);
}
