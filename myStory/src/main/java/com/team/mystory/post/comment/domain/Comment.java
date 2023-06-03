package com.team.mystory.post.comment.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team.mystory.account.user.domain.User;
import com.team.mystory.post.comment.dto.CommentRequest;
import com.team.mystory.post.post.domain.Post;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

	@Id @GeneratedValue
	private long commentId;

	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Post post;

	@Column(nullable = false , length = 200)
	private String content;

	@JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Seoul")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date postDate;

	@OneToOne(fetch = FetchType.LAZY , orphanRemoval = true)
	private User writer;

	static public Comment createComment(CommentRequest commentRequest , User user) {
		return Comment.builder()
				.content(commentRequest.getContent())
				.writer(user)
				.build();
	}
}
