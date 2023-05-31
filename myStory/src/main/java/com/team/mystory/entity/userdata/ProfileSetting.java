package com.team.mystory.entity.userdata;

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
	@Column(name = "profilesetting_id")
	private long profileNumber;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "option2")
	private int option2;
	
	static public ProfileSetting createprofileSetting() {
		ProfileSetting ps = new ProfileSetting();
		ps.setOption2(4);
		return ps;
	}
	
}
