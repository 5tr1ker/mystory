package com.team.mystory.account.profile.service;

import com.team.mystory.account.profile.dto.StatisticsResponse;
import com.team.mystory.account.user.domain.User;
import com.team.mystory.account.profile.dto.ProfileRequest;
import com.team.mystory.account.user.exception.LoginException;
import com.team.mystory.account.user.repository.LoginRepository;
import com.team.mystory.account.profile.repository.ProfileRepository;
import com.team.mystory.account.user.service.LoginService;
import com.team.mystory.common.response.ResponseMessage;
import com.team.mystory.security.jwt.support.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.team.mystory.account.profile.dto.ProfileResponse.createProfileResponse;
import static com.team.mystory.common.response.ResponseCode.REQUEST_SUCCESS;
import static com.team.mystory.common.response.message.AccountMessage.EXISTS_ACCOUNT;
import static com.team.mystory.common.response.message.AccountMessage.EXISTS_ID;
import static com.team.mystory.security.jwt.support.CookieSupport.deleteJwtTokenInCookie;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
	private final LoginRepository loginRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final LoginService loginService;
	
	public ResponseMessage<StatisticsResponse> getStatistics(String accessToken) {
		String userId = jwtTokenProvider.getUserPk(accessToken);

		return ResponseMessage.of(REQUEST_SUCCESS, profileRepository.getStatisticsOfUser(userId));
	}

	@Transactional
	public ResponseMessage updateProfile(ProfileRequest profileRequest , String token , HttpServletResponse response) {
		User user = loginService.findUserByAccessToken(token);

		if(!profileRequest.getUserId().equals(user.getId())) {
			if(loginRepository.findById(profileRequest.getUserId()).isPresent()) {
				throw new LoginException(EXISTS_ID);
			}

			user.updateId(profileRequest.getUserId());

			deleteJwtTokenInCookie(response);
		}

		user.getProfile().updateProfile(profileRequest);

		return ResponseMessage.of(REQUEST_SUCCESS);
	}

	public ResponseMessage getProfileFromUser(String token) {
		User result = loginService.findUserByAccessToken(token);

		return ResponseMessage.of(REQUEST_SUCCESS, createProfileResponse(result.getProfile() , result));
	}
}
