package com.team.mystory.post.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class CommentResponse {
    private long commentId;

    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd" , timezone = "Asia/Seoul")
    private Date postDate;

    private String writer;
}
