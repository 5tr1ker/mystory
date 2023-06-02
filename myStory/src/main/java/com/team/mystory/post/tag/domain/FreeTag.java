package com.team.mystory.post.tag.domain;

import com.team.mystory.post.post.domain.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FreeTag {

	@Id
	@GeneratedValue
	@Column(name = "freetag_id")
	private long id;
	
	@Column(name = "tagdata" , nullable = false , length = 15)
	private String tagData;

	public FreeTag(String tagData) {
		this.tagData = tagData;
	}

	public static FreeTag createFreeTag(String tag) {
		return new FreeTag(tag);
	}
	
}
