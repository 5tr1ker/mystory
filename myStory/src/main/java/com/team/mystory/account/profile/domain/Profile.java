package com.team.mystory.account.profile.domain;

import com.team.mystory.account.profile.dto.ProfileRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Profile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long profileKey;

	private String phone;
	
	@Column(nullable = false)
	private int options;
	
	static public Profile createInitProfileSetting() {
		Profile profile = new Profile();
		profile.setOptions(1);
		return profile;
	}

	public void updateProfile(ProfileRequest profileRequest) {
		this.phone = profileRequest.getPhone();
		this.options = profileRequest.getOptions();
	}
	
}
