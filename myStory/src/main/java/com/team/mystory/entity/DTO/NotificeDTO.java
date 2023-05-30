package com.team.mystory.entity.DTO;

import java.util.Date;

public class NotificeDTO {

	private Long id;
	private String writer;
	private String content;
	private String postname;
	private Date posttime;
	
	

	public NotificeDTO(Long id, String writer, String content, String postname, Date posttime) {
		this.id = id;
		this.writer = writer;
		this.content = content;
		this.postname = postname;
		this.posttime = posttime;
	}


	public NotificeDTO() {
		
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getWriter() {
		return writer;
	}


	public void setWriter(String writer) {
		this.writer = writer;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getPostname() {
		return postname;
	}


	public void setPostname(String postname) {
		this.postname = postname;
	}


	public Date getPosttime() {
		return posttime;
	}


	public void setPosttime(Date posttime) {
		this.posttime = posttime;
	}
	
	
}
