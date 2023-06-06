package com.team.mystory.post.post.repository;

import com.team.mystory.post.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> , CustomPostRepository {
	void deletePostByPostId(Long postId);

}