package com.team.mystory.post.comment.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team.mystory.account.user.domain.User;
import com.team.mystory.common.config.BooleanConverter;
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

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long commentId;

	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Post post;

	@Column(nullable = false , length = 200)
	private String content;

	@Column(nullable = false)
	@Convert(converter = BooleanConverter.class)
	private boolean isDelete;

	@JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Seoul")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date postDate;

	@OneToOne(fetch = FetchType.LAZY)
	private User writer;

	static public Comment createComment(CommentRequest commentRequest , User user) {
		return Comment.builder()
				.content(commentRequest.getContent())
				.writer(user)
				.isDelete(false)
				.build();
	}
}
