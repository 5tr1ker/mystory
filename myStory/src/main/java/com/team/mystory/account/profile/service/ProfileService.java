package com.team.mystory.account.profile.service;

import com.team.mystory.account.profile.dto.StatisticsResponse;
import com.team.mystory.account.user.domain.User;
import com.team.mystory.account.profile.domain.ProfileSetting;
import com.team.mystory.account.profile.dto.ProfileRequest;
import com.team.mystory.account.user.repository.LoginRepository;
import com.team.mystory.account.profile.repository.ProfileRepository;
import com.team.mystory.security.jwt.service.JwtService;
import com.team.mystory.security.jwt.support.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team.mystory.post.comment.repository.CommitRepository;
import com.team.mystory.post.post.repository.PostRepository;

import javax.security.auth.login.AccountException;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
	private final LoginRepository loginRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final JwtService jwtService;
	
	public StatisticsResponse getProfile(String accessToken) {
		String userId = jwtTokenProvider.getUserPk(accessToken);

		return profileRepository.getStatisticsOfUser(userId);
	}

	@Transactional
	public void updateProfile(ProfileRequest profileRequest , String token , HttpServletResponse response) throws AccountException {
		String userId = jwtTokenProvider.getUserPk(token);

		if(!profileRequest.getUserId().equals(userId)) {
			if(loginRepository.findById(profileRequest.getUserId()).isPresent()) {
				throw new AccountException("해당 아이디는 이미 존재합니다.");
			}
			User user = loginRepository.findById(userId)
					.orElseThrow(() -> new AccountException("찾을 수 없는 아이디입니다."));

			user.setId(profileRequest.getUserId());
			jwtService.deleteJwtToken(response);
		}

		ProfileSetting profileSetting = profileRepository.findProfileByUserId(profileRequest.getUserId())
				.orElseThrow(() -> new AccountException("프로필 정보를 찾을 수 없습니다."));
		profileSetting.updateProfile(profileRequest);
	}

	public ProfileSetting getProfileFromUser(String accessToken) throws AccountException {
		String userId = jwtTokenProvider.getUserPk(accessToken);

		User result = loginRepository.findById(userId)
				.orElseThrow(() -> new AccountException("존재하지 않는 사용자입니다."));

		return result.getProfileSetting();
	}
}
