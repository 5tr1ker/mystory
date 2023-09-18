package com.team.mystory.post.post.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostListResponse {
	private long numbers;
	private String title;
	private String writer;
	private String writerImage;
	@JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd" , timezone = "Asia/Seoul")
	private Date postDate;
	private int likes;
	private int views;
	private long count;
}
