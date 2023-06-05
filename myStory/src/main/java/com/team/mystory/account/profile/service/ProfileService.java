package com.team.mystory.account.profile.service;

import com.team.mystory.account.profile.dto.ProfileResponse;
import com.team.mystory.account.profile.dto.StatisticsResponse;
import com.team.mystory.account.user.domain.User;
import com.team.mystory.account.profile.domain.Profile;
import com.team.mystory.account.profile.dto.ProfileRequest;
import com.team.mystory.account.user.repository.LoginRepository;
import com.team.mystory.account.profile.repository.ProfileRepository;
import com.team.mystory.common.ResponseMessage;
import com.team.mystory.security.jwt.service.JwtService;
import com.team.mystory.security.jwt.support.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountException;

import static com.team.mystory.account.profile.dto.ProfileResponse.createProfileResponse;
import static com.team.mystory.common.ResponseCode.REQUEST_SUCCESS;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
	private final LoginRepository loginRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final JwtService jwtService;
	
	public ResponseMessage<StatisticsResponse> getProfile(String accessToken) {
		String userId = jwtTokenProvider.getUserPk(accessToken);

		return ResponseMessage.of(REQUEST_SUCCESS, profileRepository.getStatisticsOfUser(userId));
	}

	@Transactional
	public ResponseMessage updateProfile(ProfileRequest profileRequest , String token , HttpServletResponse response) throws AccountException {
		String userId = jwtTokenProvider.getUserPk(token);

		if(!profileRequest.getUserId().equals(userId)) {
			if(loginRepository.findById(profileRequest.getUserId()).isPresent()) {
				throw new AccountException("해당 아이디는 이미 존재합니다.");
			}
			User user = loginRepository.findById(userId)
					.orElseThrow(() -> new AccountException("찾을 수 없는 아이디입니다."));

			user.updateId(profileRequest.getUserId());
			jwtService.deleteJwtToken(response);
		}

		Profile profile = profileRepository.findProfileByUserId(profileRequest.getUserId())
				.orElseThrow(() -> new AccountException("프로필 정보를 찾을 수 없습니다."));
		profile.updateProfile(profileRequest);

		return ResponseMessage.of(REQUEST_SUCCESS);
	}

	public ResponseMessage getProfileFromUser(String accessToken) throws AccountException {
		String userId = jwtTokenProvider.getUserPk(accessToken);

		User result = loginRepository.findById(userId)
				.orElseThrow(() -> new AccountException("존재하지 않는 사용자입니다."));

		return ResponseMessage.of(REQUEST_SUCCESS, createProfileResponse(result.getProfile() , result.getJoinDate()));
	}
}
