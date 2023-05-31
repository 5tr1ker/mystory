package com.team.mystory.entity.freeboard;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FreeAttach {

	@Id @GeneratedValue
	@Column(name = "freeattach_id")
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false , name="freepost_id")
	private FreePost freePost;
	
	@Column(name = "file_name" , nullable = false , length = 30)
	private String fileName;

	@Column(name = "change_file" , nullable = false , length = 40)
	private String changedFile;

	@Column(name = "file_size" , nullable = false)
	private Long fileSize;

	public static FreeAttach createFreeAttach(String fileName , String changedFile , Long fileSize) {
		FreeAttach fa = new FreeAttach();
		fa.setFileName(fileName);
		fa.setChangedFile(changedFile);
		fa.setFileSize(fileSize);
		return fa;
	}
}
