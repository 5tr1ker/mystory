package com.team.mystory.post.post.repository;

import java.util.List;
import java.util.Optional;

import com.team.mystory.account.user.domain.User;
import org.springframework.data.domain.Pageable;

import com.team.mystory.post.post.dto.PostListResponse;
import com.team.mystory.post.post.domain.Post;

public interface CustomPostRepository {
	Optional<User> findRecommendationFromPost(Long postId, String userId);

	List<PostListResponse> findPostBySearch(Pageable pageable, String content);

	List<PostListResponse> findPostByTag(Pageable pageable, String tagData);

	List<PostListResponse> getPostList(Pageable pageable);

	long getTotalNumberOfPosts();

	long getTotalNumberOfTagSearchPosts(String tagData);

	long getTotalNumberOfSearchPosts(String search);

	Optional<Post> findPostByPostId(long postId);

	List<String> findTagsInPostId(long postId);

	void updatePostView(long postId);

	List<Long> findPostIdByUserId(String userId);

}
