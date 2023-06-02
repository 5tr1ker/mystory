package com.team.mystory.post.comment.service;

import com.team.mystory.post.comment.domain.FreeCommit;
import com.team.mystory.post.comment.repository.CommitRepository;
import com.team.mystory.post.post.domain.Post;
import com.team.mystory.post.post.exception.PostException;
import com.team.mystory.post.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommitRepository commitRepository;

    public List<FreeCommit> getCommit(Long postId) {
        return commitRepository.getCommit(postId);
    }

    public int addComment(String content , String writer , Long postId , Long postNumber , String postType) {
        FreeCommit fc = FreeCommit.createCommitData(content, writer , postNumber , postType); // 댓글 내용
        Post fp = postRepository.findPostByPostId(postId)
                .orElseThrow(() -> new PostException("해당 포스트를 찾을 수 없습니다."));

        fp.addFreeCommit(fc);

        return 0;
    }

}
