package com.team.mystory.post.post.service;

import com.team.mystory.account.user.domain.User;
import com.team.mystory.account.user.repository.LoginRepository;
import com.team.mystory.common.ResponseMessage;
import com.team.mystory.post.attachment.repository.AttachmentRepository;
import com.team.mystory.post.attachment.service.AttachmentService;
import com.team.mystory.post.post.domain.Post;
import com.team.mystory.post.post.dto.PostListResponse;
import com.team.mystory.post.post.dto.PostRequest;
import com.team.mystory.post.post.dto.PostResponse;
import com.team.mystory.post.post.exception.PostException;
import com.team.mystory.post.post.repository.PostRepository;
import com.team.mystory.s3.service.S3Service;
import com.team.mystory.security.jwt.support.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.AccountException;
import java.io.IOException;
import java.util.List;

import static com.team.mystory.common.ResponseCode.REQUEST_SUCCESS;
import static com.team.mystory.post.post.dto.PostResponse.createPostResponse;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final LoginRepository loginRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final AttachmentRepository attachmentRepository;
	private final AttachmentService attachmentService;
	private final S3Service s3Service;

	@Transactional
	public ResponseMessage addPost(PostRequest postRequest , List<MultipartFile> multipartFiles , String token)
			throws AccountException, IOException {
		String userId = jwtTokenProvider.getUserPk(token);
		User user = loginRepository.findById(userId)
				.orElseThrow(() -> new AccountException("사용자를 찾을 수 없습니다."));

		Post post = createNewPost(postRequest);
		user.addPost(post);
		attachmentService.fileUpload(multipartFiles , post);

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

		s3Service.deleteFileByPostId(postId);
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
	public ResponseMessage updatePost(long postId , String token , PostRequest postRequest
			, List<MultipartFile> multipartFiles) throws IOException {
		String userId = jwtTokenProvider.getUserPk(token);

		Post post = findPostByIdAndValidateOwnership(postId , userId);

		post.updatePost(postRequest);
		uploadAttachments(multipartFiles , post);
		deleteAttachments(postRequest.getDeletedFileIds(), postId);

		return ResponseMessage.of(REQUEST_SUCCESS);
	}

	public Post findPostByIdAndValidateOwnership(long postId , String userId) {
		Post post = postRepository.findPostByPostId(postId)
				.orElseThrow(() -> new PostException("해당 포스트를 찾을 수 없습니다."));
		if(!post.getWriter().getId().equals(userId)) {
			new PostException("본인이 작성한 포스트만 수정할 수 있습니다.");
		}

		return post;
	}

	public void deleteAttachments(long[] deletedFileIds, long postId) {
		attachmentService.deletedAttachment(deletedFileIds, postId);
	}

	public void uploadAttachments(List<MultipartFile> multipartFiles, Post post) throws IOException {
		attachmentService.fileUpload(multipartFiles, post);
	}

	public ResponseMessage<List<PostListResponse>> getAllPost(Pageable pageable) {
		return ResponseMessage.of(REQUEST_SUCCESS, postRepository.getPostList(pageable));
	}

	public ResponseMessage<PostResponse> findPostByPostId(long postId) {
		Post post = postRepository.findPostByPostId(postId)
				.orElseThrow(() -> new PostException("해당 포스트를 찾을 수 없습니다."));

		PostResponse postResponse = createPostResponse(post);
		postResponse.addTagData(postRepository.findTagsInPostId(postId));
		postResponse.setAttachment(attachmentRepository.findAttachmentsByPostId(postId));

		return ResponseMessage.of(REQUEST_SUCCESS , postResponse);
	}

	@Transactional
	public ResponseMessage updatePostView(long postId) {
		postRepository.updatePostView(postId);

		return ResponseMessage.of(REQUEST_SUCCESS);
	}

	public ResponseMessage<Long> getTotalNumberOfPosts(String type , String data) {
		long result = getTotalNumberAccordingType(type , data);

		return ResponseMessage.of(REQUEST_SUCCESS , result);
	}

	public long getTotalNumberAccordingType(String type , String data) {
		if(type.equals("normal")) {
			return postRepository.getTotalNumberOfPosts();
		} else if(type.equals("tag")) {
			return postRepository.getTotalNumberOfTagSearchPosts(data);
		} else if(type.equals("search")) {
			return postRepository.getTotalNumberOfSearchPosts(data);
		}
		return 0;
	}

	public ResponseMessage findPostBySearch(Pageable pageable, String postContent) {
		return ResponseMessage.of(REQUEST_SUCCESS , postRepository.findPostBySearch(pageable, postContent));
	}

	public ResponseMessage findPostBySearchAndTag(Pageable pageable, String tag) {
		return ResponseMessage.of(REQUEST_SUCCESS , postRepository.findPostByTag(pageable, tag));
	}
}
