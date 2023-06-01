package com.team.mystory.post.comment.domain;

import com.team.mystory.account.user.domain.User;
import com.team.mystory.common.PostBaseEntity;
import com.team.mystory.post.post.domain.FreePost;
import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AttributeOverrides({
	@AttributeOverride(name = "content" , column = @Column(length = 200))
})
public class FreeCommit extends PostBaseEntity {

	@Id @GeneratedValue
	@Column(name = "freecommit_id")
	private long id;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "freepost_id" , nullable = false)
	private FreePost freePost;
	
	@Column(name = "post_number" , nullable = false)
	private Long postNumber;

	@Column(name = "post_type" , nullable = false)
	private String postType;

	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	static public FreeCommit createCommitData(String content , String writer , Long postNumber , String postType) {
		FreeCommit fc = new FreeCommit();
		fc.setContent(content);
		fc.setWriter(writer);
		fc.setPostNumber(postNumber);
		fc.setPostType(postType);
		return fc;
	}
}
