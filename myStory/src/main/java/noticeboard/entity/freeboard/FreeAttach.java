package noticeboard.entity.freeboard;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class FreeAttach {

	@Id @GeneratedValue
	@Column(name = "freeattach_id")
	private long id;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false , name="freepost_id")
	private FreePost freePost;
	
	@Column(name = "file_name" , nullable = false , length = 30)
	private String fileName;
	@Column(name = "change_file" , nullable = false , length = 40)
	private String changedFile;
	@Column(name = "file_size" , nullable = false)
	private Long fileSize;
	
	// 생성 메소드
	public static FreeAttach createFreeAttach(String fileName , String changedFile , Long fileSize) {
		FreeAttach fa = new FreeAttach();
		fa.setFileName(fileName);
		fa.setChangedFile(changedFile);
		fa.setFileSize(fileSize);
		return fa;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public FreePost getFreePost() {
		return freePost;
	}

	public void setFreePost(FreePost freePost) {
		this.freePost = freePost;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getChangedFile() {
		return changedFile;
	}

	public void setChangedFile(String changedFile) {
		this.changedFile = changedFile;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	
}
