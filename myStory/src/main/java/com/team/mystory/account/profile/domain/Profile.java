package com.team.mystory.account.profile.domain;

import com.team.mystory.account.profile.dto.ProfileRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Profile {
	
	@Id
	@GeneratedValue
	private long profileKey;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(nullable = false)
	private int options;
	
	static public Profile createInitProfileSetting() {
		Profile profile = new Profile();
		profile.setOptions(4);
		return profile;
	}

	public void updateProfile(ProfileRequest profileRequest) {
		this.email = profileRequest.getEmail();
		this.phone = profileRequest.getPhone();
		this.options = profileRequest.getOption2();
	}
	
}
