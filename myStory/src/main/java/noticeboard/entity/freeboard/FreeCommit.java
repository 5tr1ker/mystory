package noticeboard.entity.freeboard;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@AttributeOverrides({
	@AttributeOverride(name = "content" , column = @Column(length = 200))
})
public class FreeCommit extends PostBaseEntity {

	@Id @GeneratedValue
	@Column(name = "freecommit_id")
	private long id;
	
	@JsonIgnore // 데이터값만 필요하므로 제거
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "freepost_id" , nullable = false)
	private FreePost freePost;
	
	@Column(name = "post_number" , nullable = false)
	private Long postNumber;
	@Column(name = "post_type" , nullable = false)
	private String postType;
	
	// 생성 메소드
	static public FreeCommit createCommitData(String content , String writer , Long postNumber , String postType) {
		FreeCommit fc = new FreeCommit();
		fc.setContent(content);
		fc.setWriter(writer);
		fc.setPostNumber(postNumber);
		fc.setPostType(postType);
		return fc;
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

	public Long getPostNumber() {
		return postNumber;
	}

	public void setPostNumber(Long postNumber) {
		this.postNumber = postNumber;
	}

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}
	
	
	
}
