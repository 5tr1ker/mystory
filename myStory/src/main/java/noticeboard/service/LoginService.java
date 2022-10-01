package noticeboard.service;

import java.util.Collections;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import noticeboard.entity.userdata.IdInfo;
import noticeboard.entity.userdata.ProfileSetting;
import noticeboard.entity.userdata.IdInfo.Admin;
import noticeboard.repository.LoginRepository;

@Service
@Transactional
public class LoginService {

	@Autowired LoginRepository loginRepository;
	
	public int register(Map<String, String> userInfo) {
		IdInfo result = loginRepository.findById(userInfo.get("id"));
		if(result != null) {
			return -1;
		}
		if(!userInfo.get("pw").equals(userInfo.get("checkpw"))) return -2;
		ProfileSetting ps = ProfileSetting.createprofileSetting();
		IdInfo idinfo_data = IdInfo.createId(userInfo.get("id"), userInfo.get("pw"));
		idinfo_data.setProfileSetting(ps);
		idinfo_data.setRoles(Collections.singletonList("ROLE_USER"));
		idinfo_data.setAdmin(Admin.GENERAL);
		loginRepository.save(idinfo_data);
		return 1;
	}
	
	public IdInfo login(Map<String , String> userInfo) {
		IdInfo result = loginRepository.findById(userInfo.get("id"));
		if(result == null || !result.getUserPassword().equals(userInfo.get("pw"))) {
			return null;
		}
		return result;
	}
	

	public String findId(Map<String,String> userInfo) {
		IdInfo result = loginRepository.findById(userInfo.get("id"));
		if(result == null) return "-1";
		String password = result.getPassword();
		return password.substring( 0 , password.length() - (password.length() - 3));
	}
	
	public ProfileSetting getProfile(String id) {
		IdInfo data = loginRepository.findById(id);
		if(data==null) return null;
		return data.getProfileSetting();
	}

	public int remove(String userInfo) {
		IdInfo data = loginRepository.findById(userInfo);
		loginRepository.delete(data);
		return 0;
	}
}
