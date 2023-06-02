package com.team.mystory.post.post.controller;

import com.team.mystory.common.ResponseMessage;
import com.team.mystory.post.post.dto.PostRequest;
import com.team.mystory.post.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
	
	private final PostService postService;
	
	@PostMapping
	public ResponseEntity<ResponseMessage> addPost(@RequestBody PostRequest postData , @CookieValue String accessToken)
			throws AccountException {

		return ResponseEntity.status(HttpStatus.CREATED).body(postService.addPost(postData , accessToken));
	}

	@GetMapping(value = "/count")
	public ResponseEntity<ResponseMessage> getTotalNumberOfPosts() {
		return ResponseEntity.ok().body(postService.getTotalNumberOfPosts());
	}

	@GetMapping
	public ResponseEntity<ResponseMessage> getAllPost(Pageable pageable) {
		return ResponseEntity.ok().body(postService.getAllPost(pageable));
	}
	
	@GetMapping(value = "/{postId}")
	public ResponseEntity<ResponseMessage> selectOnePost(@PathVariable("postId") Long postId) {
		return ResponseEntity.ok().body(postService.findPostByPostId(postId));
	}

	@DeleteMapping(value = "/{postId}")
	public ResponseEntity<ResponseMessage> deletePost(@PathVariable("postId") Long postId) {
		return ResponseEntity.ok().body(postService.deletePost(postId));
	}

	@PatchMapping(value = "/{postId}")
	public ResponseEntity<ResponseMessage> updatePost(@PathVariable("postId") Long postId , @RequestBody PostRequest postData ) {
		// attachManager.modifiedUpload(postData.getDeletedFileList() , postId);

		return ResponseEntity.ok().body(postService.updatePost(postId, postData));
	}

	@PatchMapping(value = "/views/{postId}")
	public ResponseEntity increasePostViews(@PathVariable long postId) {
		postService.updatePostView(postId);

		return ResponseEntity.ok().build();
	}
	
	@PatchMapping(value = "/likes/{postId}")
	public ResponseEntity<ResponseMessage> increasePostLike(@PathVariable Long postId , @CookieValue String accessToken) {
		return ResponseEntity.ok().body(postService.increasePostLike(postId, accessToken));
	}

	@GetMapping(value = "/search/{postContent}")
	public ResponseEntity<ResponseMessage> findPostBySearch(@PathVariable String postContent) {
		return ResponseEntity.ok().body(postService.findPostBySearch(postContent));
	}
	
	@GetMapping(value = "/search/tags/{tagData}")
	public ResponseEntity<ResponseMessage> findPostByTag(@PathVariable String tagData) {
		return ResponseEntity.ok().body(postService.findPostBySearchAndTag(tagData));
	}
}
