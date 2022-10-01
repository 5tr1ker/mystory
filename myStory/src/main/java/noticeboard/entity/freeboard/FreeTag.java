package noticeboard.entity.freeboard;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class FreeTag {

	@Id @GeneratedValue
	@Column(name = "freetag_id")
	private long id;
	
	@Column(name = "tagdata" , nullable = false , length = 15)
	private String tagData;
	
	@ManyToMany(mappedBy = "freeTag")
	private List<FreePost> freePost = new ArrayList<FreePost>();
	
	// 연관관계 편의 메소드
	public void addFreePost(FreePost freePost) {
		this.freePost.add(freePost);
		freePost.getFreeTag().add(this);
	}
	
	public FreeTag() {
		
	}
	
	public FreeTag(String tagData) {
		this.tagData = tagData;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTagData() {
		return tagData;
	}

	public void setTagData(String tagData) {
		this.tagData = tagData;
	}

	public List<FreePost> getFreePost() {
		return freePost;
	}

	public void setFreePost(List<FreePost> freePost) {
		this.freePost = freePost;
	}
	
	
	
}
