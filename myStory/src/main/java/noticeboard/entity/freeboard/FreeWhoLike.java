package noticeboard.entity.freeboard;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class FreeWhoLike {
	
	@Id @GeneratedValue
	@Column(name = "freewholike_id")
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "freepost_id")
	private FreePost freePost;
	
	@Column(nullable = false , length = 20)
	private String recommender;
	
	static public FreeWhoLike makefreeWhoLike(String userId , FreePost fp) {
		FreeWhoLike fwl = new FreeWhoLike();
		fwl.setRecommender(userId);
		fwl.setFreePost(fp);
		return fwl;
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

	public String getRecommender() {
		return recommender;
	}

	public void setRecommender(String recommender) {
		this.recommender = recommender;
	}


	
}
