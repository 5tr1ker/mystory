package com.team.mystory.account.profile.service;

import java.util.HashMap;
import java.util.Map;

import com.team.mystory.account.user.domain.IdInfo;
import com.team.mystory.account.profile.domain.ProfileSetting;
import com.team.mystory.account.profile.dto.ProfileDTO;
import com.team.mystory.account.user.repository.LoginRepository;
import com.team.mystory.account.profile.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team.mystory.post.comment.repository.CommitRepository;
import com.team.mystory.post.post.repository.PostRepository;

@Service
@Transactional
public class ProfileService {
	
	@Autowired
    ProfileRepository profileRepos;
	@Autowired
    LoginRepository loginRepos;
	@Autowired PostRepository postRepos;
	@Autowired CommitRepository commitRepos;
	
	public Map<String, String> getProfile(String idInfo) {
		Long result_totalPost = profileRepos.getTotalPost(idInfo);
		Long result_totalCommit = profileRepos.getTotalComment(idInfo);
		Long result_postView = profileRepos.getPostView(idInfo);
		String result_joindate = profileRepos.getJoinDate(idInfo);
		Map<String , String> map = new HashMap<String, String>();
		map.put("totalPost" , result_totalPost.toString());
		map.put("totalCommit" , result_totalCommit.toString());
		if(result_postView == null) map.put("postView" , "0");
		else map.put("postView" , result_postView.toString());
		map.put("joindate" , result_joindate);
		return map;
	}

	public int updateProfileData(ProfileDTO data) {
		String userId = data.getIdStatus();
		String changeId = data.getInputProfileData().getUserId();
		ProfileSetting st = profileRepos.findProfileSettings(userId);
		st.setEmail(data.getInputProfileData().getEmail());
		st.setPhone(data.getInputProfileData().getPhone());
		st.setOption2(data.getInputProfileData().getOption2());
		if(!userId.equals(changeId)) {
			IdInfo search = loginRepos.findById(changeId);
			if(search != null) return -2; // 존재하는 아이디
			IdInfo changeUserId = loginRepos.findById(userId); // 존재하지 않으면 변경
			changeUserId.setId(changeId);
			postRepos.changeWritter(userId , changeId);
			commitRepos.changeWritter(userId , changeId);
			loginRepos.save(changeUserId); // 메소드 이름 찾기는 save 해주어야함
		}
		return 0;
	}
	
}
