package com.team.mystory.entity.freeboard;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

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

	static public FreeCommit createCommitData(String content , String writer , Long postNumber , String postType) {
		FreeCommit fc = new FreeCommit();
		fc.setContent(content);
		fc.setWriter(writer);
		fc.setPostNumber(postNumber);
		fc.setPostType(postType);
		return fc;
	}
}
