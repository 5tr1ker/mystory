package com.team.mystory.post.post.domain;

import java.util.List;

import com.team.mystory.account.user.domain.User;
import com.team.mystory.common.PostBaseEntity;
import com.team.mystory.post.comment.domain.FreeCommit;
import com.team.mystory.post.tag.domain.FreeTag;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.IntSequenceGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "NUMBERS_SEQUENCE" , sequenceName = "ID_numbers" , initialValue = 1 , allocationSize = 1)
@JsonIdentityInfo(generator = IntSequenceGenerator.class , property = "id")
public class FreePost extends PostBaseEntity {
	
	@Id @GeneratedValue
	@Column(name = "freepost_id")
	private long freepostId;
	
	@Column(nullable = false)
	private long numbers;
	
	@Column(nullable = false , length = 30)
	private String title;
	
	private int views;
	private int likes;
	private boolean privates;
	@Column(name = "block_comm")
	private boolean blockComm;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idinfo_id" , nullable = false)
	private User user;

	@Builder.Default
	@ManyToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	@JoinTable(name = "freetag_id" , inverseJoinColumns = @JoinColumn(name = "freepost_id"))
	private List<FreeTag> freeTag;

	@Builder.Default
	@OneToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL , mappedBy = "freePost" , orphanRemoval = true)
	private List<FreeAttach> freeAttach;

	@Builder.Default
	@OneToMany(mappedBy = "freePost" , fetch = FetchType.LAZY , cascade = CascadeType.ALL , orphanRemoval = true)
	private List<FreeCommit> freeCommit;

	@Builder.Default
	@OneToMany(mappedBy = "freePost" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	private List<FreeWhoLike> freeWhoLike;

	
	// 연관관계 편의 메소드 시작
	public void setIdinfo(User User) {
		if(this.user != null) {
			this.user.getFreePost().remove(this);
		}
		this.user = User;
		User.getFreePost().add(this);
	}
	
	public void addFreeAttach(FreeAttach attach) {
		freeAttach.add(attach);
		attach.setFreePost(this);
	}
	public void addFreeCommit(FreeCommit commit) {
		freeCommit.add(commit);
		commit.setFreePost(this);
	}
	public void addTagData(FreeTag tags) {
		freeTag.add(tags);
		tags.getFreePost().add(this);
	}
	public void addWhoLike(FreeWhoLike wholike) {
		freeWhoLike.add(wholike);
		wholike.setFreePost(this);
	}
}
