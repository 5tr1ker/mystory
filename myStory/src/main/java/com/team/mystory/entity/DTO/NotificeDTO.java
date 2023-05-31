package com.team.mystory.entity.DTO;

import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificeDTO {

	private Long id;
	private String writer;
	private String content;
	private String postname;
	private Date posttime;

}
