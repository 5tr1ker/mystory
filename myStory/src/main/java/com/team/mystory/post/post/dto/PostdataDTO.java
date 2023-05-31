package com.team.mystory.post.post.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostdataDTO {
	private String idStatus; // 사용자
	private String[] labelData; // 태그 데이터
	private postOption postOption;	// 글 옵션
	private postContent postContent; // 글 내용
	private String[] deletedFileList; // 삭제된 파일

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public class postOption {
		private boolean blockComm;
		private boolean privates;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public class postContent {
		private String title;
		private String content;
	}
}
