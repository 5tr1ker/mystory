package com.team.mystory.account.user.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.IntSequenceGenerator;
import com.team.mystory.account.profile.domain.ProfileSetting;
import com.team.mystory.account.user.dto.LoginRequest;
import com.team.mystory.post.post.domain.Post;
import com.team.mystory.post.tag.domain.FreeTag;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = IntSequenceGenerator.class , property = "id")
public class User implements UserDetails {

	@Id @GeneratedValue
	private long userKey;
	
	@Column(nullable = false , length = 20)
	private String id;
	
	@Column(name = "password" , nullable = false , length = 45)
	private String password;

	@Temporal(TemporalType.DATE)
	@CreationTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy/MM/dd" , timezone = "Asia/Seoul")
	@Column(name = "joindate_date")
	private Date joinDate;

	@Builder.Default
	@OneToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
	@JoinColumn(name = "profilesetting_id" , nullable = false)
	private ProfileSetting profileSetting = new ProfileSetting();

	@Builder.Default
	@OneToMany(mappedBy = "writer" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	private List<Post> post = new ArrayList<>();

	@Builder.Default
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles = new ArrayList<>();

	public static User createUser(LoginRequest loginRequest) {
		return User.builder()
				.id(loginRequest.getId())
				.password(loginRequest.getPassword())
				.profileSetting(ProfileSetting.createInitProfileSetting())
				.roles(Collections.singletonList("ROLE_USER"))
				.build();
	}

	public void addPost(Post post) {
		this.post.add(post);
		post.setWriter(this);
	}

	public void updateId(String userId) {
		this.id = userId;
	}

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
		return password;
	}
}
