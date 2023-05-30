package com.team.mystory.entity.userdata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.IntSequenceGenerator;

import lombok.Builder;
import com.team.mystory.entity.freeboard.FreePost;

@SuppressWarnings("serial")
@Entity
@JsonIdentityInfo(generator = IntSequenceGenerator.class , property = "id")
public class IdInfo implements UserDetails{

	@Id @GeneratedValue
	@Column(name = "idinfo_id")
	private long idInfoID;
	
	@Column(nullable = false , length = 20)
	private String id;
	
	@Column(name = "password" , nullable = false , length = 45)
	private String userPassword;
	
	@Column(name = "ADMIN" , length = 10 , nullable = false)
	@Enumerated(EnumType.STRING)
	private Admin admin;
	
	@Temporal(TemporalType.DATE)
	@CreationTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy/MM/dd" , timezone = "Asia/Seoul")
	@Column(name = "joindate_date")
	private Date joinDate;
	
	@OneToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
	@JoinColumn(name = "profilesetting_id" , nullable = false)
	private ProfileSetting profileSetting;

	@OneToMany(mappedBy = "idInfo" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	private List<FreePost> freePost = new ArrayList<FreePost>();
	
	// 생성 메서드
	public static IdInfo createId(String id , String password) {
		IdInfo data = new IdInfo();
		data.setId(id);
		data.setUserPassword(password);
		return data;
	}
	
	@ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();
	
	public String getRoleKey(){
        return this.roles.get(0);
    }
	
	public enum Admin {
		ADMin , GENERAL
	}
	// UserDetail 시작
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles.stream()
				.map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
	}

	@Override
	public String getUsername() {
		return id;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}
	// userDetail 끝
	
	// 연관관계 매핑
	public void addFreePost(FreePost post) {
		this.freePost.add(post);
		post.setIdinfo(this);
	}

	public long getIdInfoID() {
		return idInfoID;
	}

	public void setIdInfoID(long idInfoID) {
		this.idInfoID = idInfoID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public ProfileSetting getProfileSetting() {
		return profileSetting;
	}

	public void setProfileSetting(ProfileSetting profileSetting) {
		this.profileSetting = profileSetting;
	}

	public List<FreePost> getFreePost() {
		return freePost;
	}

	public void setFreePost(List<FreePost> freePost) {
		this.freePost = freePost;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	
	
	
}
