package com.team.mystory.post.tag.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long tagId;
	
	@Column(nullable = false , length = 15)
	private String tagData;

	public Tag(String tagData) {
		this.tagData = tagData;
	}

	public static Tag createFreeTag(String tag) {
		return new Tag(tag);
	}
	
}
