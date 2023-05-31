package com.team.mystory.entity.DTO;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO {
	private String idStatus;
	private inputProfileData inputProfileData;

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public class inputProfileData {
		private String userId;
		private String email;
		private String phone;
		private Integer option2;
	}

} 
