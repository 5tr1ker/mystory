package noticeboard.entity.freeboard;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.IntSequenceGenerator;

import noticeboard.entity.userdata.IdInfo;
// 게시물 데이터
@Entity
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
	
	@JsonIgnore // JSON response 에서 제외
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idinfo_id" , nullable = false)
	private IdInfo idInfo;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	@JoinTable(name = "freetag_id" , 
	inverseJoinColumns = @JoinColumn(name = "freepost_id"))
	private List<FreeTag> freeTag = new ArrayList<FreeTag>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL , mappedBy = "freePost" , orphanRemoval = true)
	private List<FreeAttach> freeAttach = new ArrayList<FreeAttach>();

	@JsonIgnore
	@OneToMany(mappedBy = "freePost" , fetch = FetchType.LAZY , cascade = CascadeType.ALL , orphanRemoval = true)
	private List<FreeCommit> freeCommit = new ArrayList<FreeCommit>();

	@JsonIgnore
	@OneToMany(mappedBy = "freePost" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	private List<FreeWhoLike> freeWhoLike = new ArrayList<FreeWhoLike>();

	
	// 연관관계 편의 메소드 시작
	public void setIdinfo(IdInfo IdInfo) {
		if(this.idInfo != null) {
			this.idInfo.getFreePost().remove(this);
		}
		this.idInfo = IdInfo;
		IdInfo.getFreePost().add(this);
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
	// 연관관계 편의 메소드 종료

	public long getID_numbers() {
		return freepostId;
	}

	public void setID_numbers(long iD_numbers) {
		freepostId = iD_numbers;
	}

	public long getNumbers() {
		return numbers;
	}

	public void setNumbers(long numbers) {
		this.numbers = numbers;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public boolean isPrivates() {
		return privates;
	}

	public void setPrivates(boolean privates) {
		this.privates = privates;
	}

	public boolean isBlockComm() {
		return blockComm;
	}

	public void setBlockComm(boolean blockComm) {
		this.blockComm = blockComm;
	}

	public IdInfo getIdInfo() {
		return idInfo;
	}

	public void setIdInfo(IdInfo idInfo) {
		this.idInfo = idInfo;
	}

	public List<FreeTag> getFreeTag() {
		return freeTag;
	}

	public void setFreeTag(List<FreeTag> freeTag) {
		this.freeTag = freeTag;
	}

	public List<FreeAttach> getFreeAttach() {
		return freeAttach;
	}

	public void setFreeAttach(List<FreeAttach> freeAttach) {
		this.freeAttach = freeAttach;
	}

	public List<FreeCommit> getFreeCommit() {
		return freeCommit;
	}

	public void setFreeCommit(List<FreeCommit> freeCommit) {
		this.freeCommit = freeCommit;
	}

	public List<FreeWhoLike> getFreeWhoLike() {
		return freeWhoLike;
	}

	public void setFreeWhoLike(List<FreeWhoLike> freeWhoLike) {
		this.freeWhoLike = freeWhoLike;
	}
	

}
