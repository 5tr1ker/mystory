package com.team.mystory.post.post.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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
}
