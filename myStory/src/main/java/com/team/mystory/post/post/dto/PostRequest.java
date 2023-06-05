package com.team.mystory.post.post.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
	private String[] tags;
	private boolean blockComment;
	private boolean privatePost;
	private String title;
	private String content;
	private long[] deletedFileIds;
}
