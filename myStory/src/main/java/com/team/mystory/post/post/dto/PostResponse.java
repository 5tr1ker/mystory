package com.team.mystory.post.post.dto;

import com.team.mystory.post.attachment.dto.AttachmentResponse;
import com.team.mystory.post.post.domain.Post;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class PostResponse {
    private long postId;
    private String title;
    private String content;
    private int views;
    private int likes;
    private boolean isPrivate;
    private boolean isBlockComment;

    @Builder.Default
    List<String> tags = new ArrayList<>();

    @Builder.Default
    List<AttachmentResponse> attachment = new ArrayList<>();

    public static PostResponse createPostResponse(Post post) {
        return PostResponse.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .views(post.getViews())
                .likes(post.getLikes())
                .isPrivate(post.isPrivate())
                .isBlockComment(post.isBlockComment())
                .build();
    }

    public void addTagData(List<String> tags) {
        this.tags = tags;
    }

}
