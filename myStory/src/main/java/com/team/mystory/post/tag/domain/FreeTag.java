package com.team.mystory.post.tag.domain;

import com.team.mystory.post.post.domain.FreePost;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
public class FreeTag {

	@Id
	@GeneratedValue
	@Column(name = "freetag_id")
	private long id;
	
	@Column(name = "tagdata" , nullable = false , length = 15)
	private String tagData;

	@Builder.Default
	@ManyToMany(mappedBy = "freeTag")
	private List<FreePost> freePost;


	public void addFreePost(FreePost freePost) {
		this.freePost.add(freePost);
		freePost.getFreeTag().add(this);
	}

	public FreeTag() {

	}

	public FreeTag(String tagData) {
		this.tagData = tagData;
	}
	
}
