package com.team.mystory.account.user.service;

import com.team.mystory.account.profile.domain.ProfileSetting;
import com.team.mystory.account.user.domain.User;
import com.team.mystory.account.user.dto.LoginRequest;
import com.team.mystory.account.user.repository.LoginRepository;
import com.team.mystory.common.ResponseMessage;
import com.team.mystory.security.jwt.dto.Token;
import com.team.mystory.security.jwt.service.JwtService;
import com.team.mystory.security.jwt.support.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountException;

import static com.team.mystory.common.ResponseCode.REQUEST_SUCCESS;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

	private final LoginRepository loginRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final JwtService jwtService;

	public void validNewAccountVerification(LoginRequest loginRequest) throws AccountException {
		if(loginRepository.findById(loginRequest.getId()).isPresent()) {
			throw new AccountException("이미 존재하는 사용자입니다.");
		}

		if(!loginRequest.getPassword().equals(loginRequest.getCheckPassword())) {
			throw new AccountException("비밀번호가 일치하지 않습니다.");
		}
	}
	
	public ResponseMessage register(LoginRequest loginRequest) throws AccountException {
		validNewAccountVerification(loginRequest);

		loginRepository.save(User.createUser(loginRequest));
		return ResponseMessage.of(REQUEST_SUCCESS);
	}
	
	public ResponseMessage login(LoginRequest loginRequest , HttpServletResponse response) throws AccountException {
		User result = loginRepository.findById(loginRequest.getId())
				.orElseThrow(() -> new AccountException("사용자를 찾을 수 없습니다."));

		if(!result.getPassword().equals(loginRequest.getPassword())) {
			throw new AccountException("비밀번호가 일치하지 않습니다.");
		}

		createJwtToken(result , response);
		
		return ResponseMessage.of(REQUEST_SUCCESS);
	}

	public void createJwtToken(User user , HttpServletResponse response) {
		Token tokenDTO = jwtTokenProvider.createAccessToken(user.getUsername(), user.getRoles());
		jwtService.login(tokenDTO);

		Cookie refreshToken = new Cookie("refreshToken", tokenDTO.getRefreshToken());
		refreshToken.setPath("/");
		refreshToken.setMaxAge(14 * 24 * 60 * 60 * 1000);
		refreshToken.setSecure(true);
		refreshToken.setHttpOnly(true);

		Cookie accessToken = new Cookie("accessToken", tokenDTO.getRefreshToken());
		accessToken.setPath("/");
		accessToken.setMaxAge(30 * 60 * 1000);
		accessToken.setSecure(true);
		accessToken.setHttpOnly(true);

		response.addCookie(refreshToken);
		response.addCookie(accessToken);
	}

	public ResponseMessage getPartPasswordFromId(LoginRequest loginRequest) throws AccountException {
		User result = loginRepository.findById(loginRequest.getId())
				.orElseThrow(() -> new AccountException("존재하지 않는 사용자입니다."));

		String password = result.getPassword();
		String partPassword = password.substring( 0 , password.length() - (password.length() - 3));
		return ResponseMessage.of(REQUEST_SUCCESS , partPassword);
	}

	public User getUserByUserId(String userId) throws AccountException {
		return loginRepository.findById(userId)
				.orElseThrow(() -> new AccountException("존재하지 않는 사용자입니다."));
	}

	public ResponseMessage removeUser(String accessToken) throws AccountException {
		String userId = jwtTokenProvider.getUserPk(accessToken);
		User result = loginRepository.findById(userId)
				.orElseThrow(() -> new AccountException("존재하지 않는 사용자입니다."));

		loginRepository.delete(result);

		return ResponseMessage.of(REQUEST_SUCCESS);
	}
}
