package com.team.mystory.post.comment.service;

import com.team.mystory.account.user.domain.User;
import com.team.mystory.account.user.exception.LoginException;
import com.team.mystory.account.user.repository.LoginRepository;
import com.team.mystory.common.response.ResponseMessage;
import com.team.mystory.common.response.message.AccountMessage;
import com.team.mystory.common.response.message.PostMessage;
import com.team.mystory.post.comment.domain.Comment;
import com.team.mystory.post.comment.dto.CommentRequest;
import com.team.mystory.post.comment.dto.CommentResponse;
import com.team.mystory.post.comment.exception.CommentException;
import com.team.mystory.post.comment.repository.CommentRepository;
import com.team.mystory.post.post.domain.Post;
import com.team.mystory.post.post.exception.PostException;
import com.team.mystory.post.post.repository.PostRepository;
import com.team.mystory.security.jwt.support.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountException;
import java.util.List;

import static com.team.mystory.common.response.ResponseCode.REQUEST_SUCCESS;
import static com.team.mystory.common.response.message.AccountMessage.NOT_FOUNT_ACCOUNT;
import static com.team.mystory.common.response.message.CommentMessage.ONLY_OWNER_CAN_DELETE;
import static com.team.mystory.common.response.message.PostMessage.NOT_FOUNT_POST;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final LoginRepository loginRepository;

    public ResponseMessage<List<Comment>> getCommit(Long postId) {
        return ResponseMessage.of(REQUEST_SUCCESS , commentRepository.findCommentByPostId(postId));
    }

    @Transactional
    public ResponseMessage addComment(CommentRequest commentRequest , String token) {
        String userId = jwtTokenProvider.getUserPk(token);

        User user = loginRepository.findById(userId)
                .orElseThrow(() -> new LoginException(NOT_FOUNT_ACCOUNT));
        Post post = postRepository.findPostByPostId(commentRequest.getPostId())
                .orElseThrow(() -> new PostException(NOT_FOUNT_POST));

        Comment comment = Comment.createComment(commentRequest , user);
        post.addFreeCommit(comment);

        return ResponseMessage.of(REQUEST_SUCCESS);
    }

    public ResponseMessage deleteCommentByCommentId(long commentId , String token) {
        String userId = jwtTokenProvider.getUserPk(token);

        commentRepository.findCommentByCommentIdAndUserId(commentId , userId)
                .orElseThrow(() -> new CommentException(ONLY_OWNER_CAN_DELETE));

        commentRepository.deleteById(commentId);

        return ResponseMessage.of(REQUEST_SUCCESS);
    }

    public ResponseMessage<List<CommentResponse>> getNotificationFromUser(String token) {
        String userId = jwtTokenProvider.getUserPk(token);

        loginRepository.findById(userId).orElseThrow(() -> new LoginException(NOT_FOUNT_ACCOUNT));

        return ResponseMessage.of(REQUEST_SUCCESS, commentRepository.findCommentByCommentPostWithoutMe(userId));
    }
}