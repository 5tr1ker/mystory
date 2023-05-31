package com.team.mystory.entity.userdata;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.IntSequenceGenerator;
import com.team.mystory.entity.freeboard.FreePost;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
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

	@Builder.Default
	@OneToMany(mappedBy = "idInfo" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	private List<FreePost> freePost;

	public static IdInfo createId(String id , String password) {
		IdInfo data = new IdInfo();
		data.setId(id);
		data.setUserPassword(password);
		return data;
	}

	@Builder.Default
	@ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;
	
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

	public void addFreePost(FreePost post) {
		this.freePost.add(post);
		post.setIdinfo(this);
	}
}
