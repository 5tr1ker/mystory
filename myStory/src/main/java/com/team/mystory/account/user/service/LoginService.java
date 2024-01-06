package com.team.mystory.account.user.service;

import com.team.mystory.account.user.constant.UserType;
import com.team.mystory.account.user.domain.User;
import com.team.mystory.account.user.dto.LoginRequest;
import com.team.mystory.account.user.dto.PasswordRequest;
import com.team.mystory.account.user.dto.UserResponse;
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

import static com.team.mystory.common.response.ResponseCode.LOGIN_SUCCESS;
import static com.team.mystory.common.response.ResponseCode.REQUEST_SUCCESS;
import static com.team.mystory.security.jwt.support.CookieSupport.*;

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
			throw new AccountException("이미 존재하는 사용자입니다.");
		}

		if(!loginRequest.getPassword().equals(loginRequest.getCheckPassword())) {
			throw new AccountException("비밀번호가 일치하지 않습니다.");
		}
	}
	
	public ResponseMessage register(LoginRequest loginRequest , MultipartFile multipartFile) throws AccountException, IOException {
		validNewAccountVerification(loginRequest);
		String url = s3Service.uploadImageToS3(multipartFile);

		loginRepository.save(User.createGeneralUser(loginRequest , url , bCryptPasswordEncoder.encode(loginRequest.getPassword())));
		return ResponseMessage.of(REQUEST_SUCCESS);
	}

	public User isValidAccount(LoginRequest request) throws AccountException {
		User result = loginRepository.findById(request.getId())
				.orElseThrow(() -> new AccountException("사용자를 찾을 수 없습니다."));

		if(result.getUserType().equals(UserType.OAUTH_USER)) {
			throw new AccountException("해당 계정은 OAuth2.0 사용자입니다.");
		}

		if(!result.checkPassword(request.getPassword() , bCryptPasswordEncoder)) {
			throw new AccountException("비밀번호가 일치하지 않습니다.");
		}

		if(result.isSuspension() && result.getSuspensionDate().compareTo(LocalDate.now()) > 0) {
			throw new AccountException("해당 계정은 " + result.getSuspensionDate() + " 일 까지 정지입니다. \n사유 : " + result.getSuspensionReason());
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

	public void isExistEmail(String userId) throws AccountException {
		if(loginRepository.findByEmail(userId).isPresent()) {
			throw new AccountException("이미 존재하는 이메일입니다.");
		};
	}

	public ResponseMessage findUserByToken(String token) throws AccountException {
		String userId = jwtTokenProvider.getUserPk(token);
		UserResponse userResponse =  loginRepository.findUserResponseById(userId)
				.orElseThrow(() -> new AccountException("존재하지 않는 사용자입니다."));

		return ResponseMessage.of(REQUEST_SUCCESS , userResponse);
	}

	public ResponseMessage removeUser(String accessToken , HttpServletResponse response) throws AccountException {
		User result = findUserByAccessToken(accessToken);

		deleteAllS3FilesUploadedByUserId(result.getId());
		loginRepository.delete(result);

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
	public String modifyProfileImage(String accessToken, MultipartFile multipartFile) throws AccountException, IOException {
		User result = findUserByAccessToken(accessToken);

		if(result.getProfileImage() != null && !result.getProfileImage().isEmpty()) {
			s3Service.deleteFile(result.getProfileImage());
		}

		String url = s3Service.uploadImageToS3(multipartFile);

		result.updateProfileImage(url);

		return url;
	}

	public User findUserByAccessToken(String accessToken) throws AccountException {
		String userId = jwtTokenProvider.getUserPk(accessToken);
		return loginRepository.findById(userId)
				.orElseThrow(() -> new AccountException("존재하지 않는 사용자입니다."));
	}

	@Transactional
    public void modifyPassword(PasswordRequest request) throws AccountException {
		User user = loginRepository.findByEmail(request.getEmail())
						.orElseThrow(() -> new AccountException("존재하지 않는 사용자입니다."));

		user.updatePassword(bCryptPasswordEncoder.encode(request.getPassword()));
    }
}
