package com.team.mystory.account.profile.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProfileSetting {
	
	@Id
	@GeneratedValue
	private long profileKey;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "option2")
	private int option2;
	
	static public ProfileSetting createInitProfileSetting() {
		ProfileSetting profileSetting = new ProfileSetting();
		profileSetting.setOption2(4);
		return profileSetting;
	}
	
}
