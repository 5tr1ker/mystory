package com.team.mystory.account.user.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.IntSequenceGenerator;
import com.team.mystory.account.profile.domain.Profile;
import com.team.mystory.account.user.constant.UserRole;
import com.team.mystory.account.user.constant.UserType;
import com.team.mystory.account.user.dto.LoginRequest;
import com.team.mystory.meeting.meeting.domain.Meeting;
import com.team.mystory.post.post.domain.Post;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = IntSequenceGenerator.class , property = "id")
public class User implements UserDetails {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userKey;
	
	@Column(nullable = false , length = 30)
	private String id;
	
	@Column(nullable = false)
	private String password;

	private String profileImage;

	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false)
	private UserRole role;

	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false)
	private UserType userType;

	@Temporal(TemporalType.DATE)
	@CreationTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy/MM/dd" , timezone = "Asia/Seoul")
	@Column(nullable = false)
	private Date joinDate;

	@Builder.Default
	@OneToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
	@JoinColumn(nullable = false)
	private Profile profile = new Profile();

	@Builder.Default
	@OneToMany(mappedBy = "writer" , cascade = CascadeType.ALL)
	private List<Post> post = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "meetingOwner" , cascade = CascadeType.ALL)
	private List<Meeting> meetings = new ArrayList<>();

	public static User createGeneralUser(LoginRequest loginRequest , String url , String password) {
		return User.builder()
				.id(loginRequest.getId())
				.profileImage(url)
				.password(password)
				.profile(Profile.createInitProfileSetting())
				.role(UserRole.USER)
				.userType(UserType.GENERAL_USER)
				.build();
	}

	public static User createOAuthUser(String userId) {
		return User.builder()
				.id(userId)
				.password(UUID.randomUUID().toString())
				.profile(Profile.createInitProfileSetting())
				.role(UserRole.USER)
				.userType(UserType.OAUTH_USER)
				.build();
	}

	public void addPost(Post post) {
		this.post.add(post);
		post.setWriter(this);
	}

	public boolean checkPassword(String plainPassword, PasswordEncoder passwordEncoder) {
		return passwordEncoder.matches(plainPassword, this.password);
	}

	public void updateId(String userId) {
		this.id = userId;
	}

	public void updateProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority(role.getRole()));
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
