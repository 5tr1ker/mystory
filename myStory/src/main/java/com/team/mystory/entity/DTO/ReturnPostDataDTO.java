package com.team.mystory.entity.DTO;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	
	public long getNumbers() {
		return numbers;
	}
	public void setNumbers(long numbers) {
		this.numbers = numbers;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getwriter() {
		return writer;
	}
	public void setwriter(String writer) {
		this.writer = writer;
	}
	public Date getpostTime() {
		return postTime;
	}
	public void setpostTime(Date postTime) {
		this.postTime = postTime;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public int getviews() {
		return views;
	}
	public void setviews(int views) {
		this.views = views;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public boolean isBlockcomm() {
		return blockcomm;
	}
	public void setBlockcomm(boolean blockcomm) {
		this.blockcomm = blockcomm;
	}
	public boolean isPrivates() {
		return privates;
	}
	public void setPrivates(boolean privates) {
		this.privates = privates;
	}
	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}
	
}
