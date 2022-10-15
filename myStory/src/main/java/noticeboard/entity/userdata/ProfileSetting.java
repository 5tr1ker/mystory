package noticeboard.entity.userdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ProfileSetting {
	
	@Id @GeneratedValue
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

	public long getProfileNumber() {
		return profileNumber;
	}

	public void setProfileNumber(long profileNumber) {
		this.profileNumber = profileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getOption2() {
		return option2;
	}

	public void setOption2(int option2) {
		this.option2 = option2;
	}
	
	
	
}
