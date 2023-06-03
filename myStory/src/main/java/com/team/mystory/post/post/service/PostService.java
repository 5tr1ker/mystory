package com.team.mystory.post.post.service;

import com.team.mystory.account.user.domain.User;
import com.team.mystory.account.user.repository.LoginRepository;
import com.team.mystory.common.ResponseMessage;
import com.team.mystory.post.post.domain.Post;
import com.team.mystory.post.post.dto.PostListResponse;
import com.team.mystory.post.post.dto.PostRequest;
import com.team.mystory.post.post.dto.PostResponse;
import com.team.mystory.post.post.exception.PostException;
import com.team.mystory.post.post.repository.PostRepository;
import com.team.mystory.security.jwt.support.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountException;
import java.util.List;

import static com.team.mystory.common.ResponseCode.REQUEST_SUCCESS;
import static com.team.mystory.post.post.dto.PostResponse.createPostResponse;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final LoginRepository loginRepository;
	private final JwtTokenProvider jwtTokenProvider;

	@Transactional
	public ResponseMessage addPost(PostRequest postRequest , String token) throws AccountException {
		String userId = jwtTokenProvider.getUserPk(token);
		User user = loginRepository.findById(userId)
				.orElseThrow(() -> new AccountException("사용자를 찾을 수 없습니다."));

		user.addPost(createNewPost(postRequest));

		return ResponseMessage.of(REQUEST_SUCCESS);
	}

	public Post createNewPost(PostRequest postRequest) {
		Post post = Post.createPost(postRequest);
		post.addTagFromTagList(postRequest.getTags());

		return post;
	}

	@Transactional
	public ResponseMessage deletePost(long postId , String token) {
		String userId = jwtTokenProvider.getUserPk(token);

		postRepository.findPostByPostIdAndUserId(postId , userId)
				.orElseThrow(() -> new PostException("본인이 작성한 포스트만 삭제할 수 있습니다."));

		postRepository.deletePostByPostId(postId);

		return ResponseMessage.of(REQUEST_SUCCESS);
	}

	@Transactional
	public ResponseMessage increasePostLike(long postId , String token) throws AccountException {
		String userId = jwtTokenProvider.getUserPk(token);

		postRepository.findRecommendationFromPost(postId , userId)
				.ifPresent(a -> { throw new PostException("이미 추천을 눌렀습니다."); });
		Post post = postRepository.findPostByPostId(postId)
				.orElseThrow(() -> new PostException("해당 포스트를 찾을 수 없습니다."));
		User user = loginRepository.findById(userId)
				.orElseThrow(() -> new AccountException("사용자를 찾을 수 없습니다."));

		post.addRecommendation(user);

		return ResponseMessage.of(REQUEST_SUCCESS);
	}

	@Transactional
	public ResponseMessage updatePost(long postId , String token , PostRequest postRequest) {
		String userId = jwtTokenProvider.getUserPk(token);

		Post post = postRepository.findPostByPostId(postId)
				.orElseThrow(() -> new PostException("해당 포스트를 찾을 수 없습니다."));
		postRepository.findPostByPostIdAndUserId(postId , userId)
				.orElseThrow(() -> new PostException("본인이 작성한 포스트만 수정할 수 있습니다."));

		post.updatePost(postRequest);

		return ResponseMessage.of(REQUEST_SUCCESS);
	}

	public ResponseMessage<List<PostListResponse>> getAllPost(Pageable pageable) {
		return ResponseMessage.of(REQUEST_SUCCESS, postRepository.getPostList(pageable));
	}

	/**
	 * @Fix-up
	 *
	 *  첨부파일 추가 요망
	 */
	public ResponseMessage<PostResponse> findPostByPostId(long postId) {
		Post post = postRepository.findPostByPostId(postId)
				.orElseThrow(() -> new PostException("해당 포스트를 찾을 수 없습니다."));

		PostResponse postResponse = createPostResponse(post);
		postResponse.addTagData(postRepository.findTagsInPostId(postId));

		return ResponseMessage.of(REQUEST_SUCCESS , postResponse);
	}

	@Transactional
	public ResponseMessage updatePostView(long postId) {
		postRepository.updatePostView(postId);

		return ResponseMessage.of(REQUEST_SUCCESS);
	}

	public ResponseMessage<Long> getTotalNumberOfPosts() {
		return ResponseMessage.of(REQUEST_SUCCESS , postRepository.getTotalNumberOfPosts());
	}

	public ResponseMessage findPostBySearch(Pageable pageable, String postContent) {
		return ResponseMessage.of(REQUEST_SUCCESS , postRepository.findPostBySearch(pageable, postContent));
	}

	public ResponseMessage findPostBySearchAndTag(Pageable pageable, String tag) {
		return ResponseMessage.of(REQUEST_SUCCESS , postRepository.findPostByTag(pageable, tag));
	}
}
