package com.team.mystory.post.comment.repository;

import com.team.mystory.post.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> , CustomCommentRepository {

}
