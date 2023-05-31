package com.team.mystory.entity.DTO;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReturnPostDataDTO {
	private long numbers;
	private String title;
	private String writer;
	@JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd" , timezone = "Asia/Seoul")
	private Date postTime;
	private int likes;
	private int views;
	private Long count;
	/*
	 * POSTviews 전용
	 */
	private String content;
	private boolean blockcomm;
	private boolean privates;
	
	/* postviews */
	public ReturnPostDataDTO(long numbers, String title, String writer, Date postTime, int likes, int views,
			String content, boolean blockcomm, boolean privates) {
		super();
		this.numbers = numbers;
		this.title = title;
		this.writer = writer;
		this.postTime = postTime;
		this.likes = likes;
		this.views = views;
		this.content = content;
		this.blockcomm = blockcomm;
		this.privates = privates;
	}
	
	/* 검색 전용 */
	public ReturnPostDataDTO(long numbers, String title, String writer, Date postTime, int likes, int views) {
		this.numbers = numbers;
		this.title = title;
		this.writer = writer;
		this.postTime = postTime;
		this.likes = likes;
		this.views = views; 
	}

	/* 목록 전용 */
	public ReturnPostDataDTO(long numbers, String title, String writer, Date postTime, int likes, int views , Long count) {
		this.numbers = numbers;
		this.title = title;
		this.writer = writer;
		this.postTime = postTime;
		this.likes = likes;
		this.views = views;
		this.count= count; 
	}

}
