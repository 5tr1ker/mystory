package com.team.mystory.account.user.service;

import com.team.mystory.account.user.constant.UserType;
import com.team.mystory.account.user.domain.User;
import com.team.mystory.account.user.dto.LoginRequest;
import com.team.mystory.account.user.dto.PasswordRequest;
import com.team.mystory.account.user.dto.UserResponse;
import com.team.mystory.account.user.exception.LoginException;
import com.team.mystory.account.user.repository.LoginRepository;
import com.team.mystory.common.response.ResponseMessage;
import com.team.mystory.post.post.repository.PostRepository;
import com.team.mystory.s3.service.S3Service;
import com.team.mystory.security.jwt.dto.Token;
import com.team.mystory.security.jwt.service.JwtService;
import com.team.mystory.security.jwt.support.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.AccountException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static com.team.mystory.account.user.dto.UserResponse.createResponse;
import static com.team.mystory.common.response.Message.*;
import static com.team.mystory.common.response.ResponseCode.LOGIN_SUCCESS;
import static com.team.mystory.common.response.ResponseCode.REQUEST_SUCCESS;
import static com.team.mystory.security.jwt.support.CookieSupport.deleteJwtTokenInCookie;
import static com.team.mystory.security.jwt.support.CookieSupport.setCookieFromJwt;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

	private final LoginRepository loginRepository;
	private final PostRepository postRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final S3Service s3Service;
	private final JwtService jwtService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public void validNewAccountVerification(LoginRequest loginRequest) throws AccountException {
		if(loginRepository.findById(loginRequest.getId()).isPresent()) {
			throw new LoginException(EXISTS_ACCOUNT);
		}

		if(!loginRequest.getPassword().equals(loginRequest.getCheckPassword())) {
			throw new LoginException(NOT_MATCH_PASSWORD);
		}
	}
	
	public ResponseMessage register(LoginRequest loginRequest , MultipartFile multipartFile) throws AccountException, IOException {
		validNewAccountVerification(loginRequest);
		String url = s3Service.uploadImageToS3(multipartFile);

		loginRepository.save(User.createGeneralUser(loginRequest , url , bCryptPasswordEncoder.encode(loginRequest.getPassword())));
		return ResponseMessage.of(REQUEST_SUCCESS);
	}

	public User isValidAccount(LoginRequest request) {
		User result = loginRepository.findById(request.getId())
				.orElseThrow(() -> new LoginException(NOT_FOUNT_ACCOUNT));

		if(result.getUserType().equals(UserType.OAUTH_USER)) {
			throw new LoginException(UNUSUAL_APPROACH);
		}

		if(!result.checkPassword(request.getPassword() , bCryptPasswordEncoder)) {
			throw new LoginException(NOT_MATCH_PASSWORD);
		}

		if(result.isSuspension() && result.getSuspensionDate().compareTo(LocalDate.now()) > 0) {
			throw new LoginException("해당 계정은 " + result.getSuspensionDate() + " 일 까지 정지입니다. \n사유 : " + result.getSuspensionReason());
		}

		return result;
	}
	
	public ResponseMessage login(LoginRequest loginRequest , HttpServletResponse response) throws AccountException {
		User result = isValidAccount(loginRequest);
		result.updateLoginDate();

		createJwtToken(result , response);

		return ResponseMessage.of(LOGIN_SUCCESS);
	}

	public void createJwtToken(User user , HttpServletResponse response) {
		Token token = jwtTokenProvider.createJwtToken(user.getUsername(), user.getRole());
		jwtService.login(token);

		setCookieFromJwt(token , response);
	}

	public void isExistEmail(String userId) {
		if(loginRepository.findByEmail(userId).isPresent()) {
			throw new LoginException(EXISTS_EMAIL);
		};
	}

	public ResponseMessage findUserByToken(String token) {
		UserResponse userResponse = createResponse(findUserByAccessToken(token));

		return ResponseMessage.of(REQUEST_SUCCESS , userResponse);
	}

	public ResponseMessage removeUser(String accessToken , HttpServletResponse response) {
		User result = findUserByAccessToken(accessToken);

		deleteAllS3FilesUploadedByUserId(result.getId());
		result.deleteUser();

		deleteJwtTokenInCookie(response);

		return ResponseMessage.of(REQUEST_SUCCESS);
	}

	public void deleteAllS3FilesUploadedByUserId(String userId) {
		List<Long> postIds = postRepository.findPostIdByUserId(userId);

		for(long postId : postIds) {
			s3Service.deleteFileByPostId(postId);
		}
	}

	@Transactional
	public String modifyProfileImage(String accessToken, MultipartFile multipartFile) throws IOException {
		User result = findUserByAccessToken(accessToken);

		if(result.getProfileImage() != null && !result.getProfileImage().isEmpty()) {
			s3Service.deleteFile(result.getProfileImage());
		}

		String url = s3Service.uploadImageToS3(multipartFile);

		result.updateProfileImage(url);

		return url;
	}

	public User findUserByAccessToken(String accessToken) {
		String userId = jwtTokenProvider.getUserPk(accessToken);

		return loginRepository.findById(userId)
				.orElseThrow(() -> new LoginException(NOT_FOUNT_ACCOUNT));
	}

	@Transactional
    public void modifyPassword(PasswordRequest request) {
		User user = loginRepository.findByEmail(request.getEmail())
						.orElseThrow(() -> new LoginException(NOT_FOUNT_ACCOUNT));

		user.updatePassword(bCryptPasswordEncoder.encode(request.getPassword()));
    }
}
