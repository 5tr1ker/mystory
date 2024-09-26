package com.team.mystory.account.user.controller;

import com.team.mystory.account.user.dto.LoginRequest;
import com.team.mystory.account.user.dto.PasswordRequest;
import com.team.mystory.account.user.service.LoginService;
import com.team.mystory.common.response.ResponseMessage;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.AccountException;
import java.io.IOException;

import static com.team.mystory.common.response.ResponseCode.LOGIN_SUCCESS;
import static com.team.mystory.common.response.ResponseCode.LOGOUT_SUCCESS;
import static com.team.mystory.security.jwt.support.CookieSupport.deleteJwtTokenInCookie;

@RestController
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;

	@PostMapping(value = "/registers")
	public ResponseEntity<ResponseMessage> register(@RequestPart(name = "data") LoginRequest loginRequest , @RequestPart(name = "image") MultipartFile multipartFile)
			throws AccountException, IOException {
		return ResponseEntity.ok().body(loginService.register(loginRequest , multipartFile));
	}

	@PatchMapping("/password")
	public ResponseEntity modifyPassword(@RequestBody PasswordRequest request) {
		loginService.modifyPassword(request);

		return ResponseEntity.ok().build();
	}

	@PatchMapping(value = "/profile-image")
	public ResponseEntity modifyProfileImage(@RequestPart(name = "image") MultipartFile multipartFile , @CookieValue String accessToken) throws IOException {
		String result = loginService.modifyProfileImage(accessToken , multipartFile);

		return ResponseEntity.ok().body(result);
	}

	@PostMapping(value = "/logins")
	public ResponseEntity<ResponseMessage> login(@RequestBody LoginRequest request , HttpServletResponse response) {
		loginService.login(request, response);

		return ResponseEntity.ok().body(ResponseMessage.of(LOGIN_SUCCESS));
	}

	@PostMapping(value = "/user/logout")
	public ResponseEntity logout(HttpServletResponse response) {
		deleteJwtTokenInCookie(response);

		return ResponseEntity.ok().build();
	}

	@DeleteMapping(value = "/users")
	public ResponseEntity deleteUser(@CookieValue String accessToken , HttpServletResponse response) {
		loginService.removeUser(accessToken , response);

		return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "/users/{email}/{id}")
	public ResponseEntity<ResponseMessage> isExistAccount(@PathVariable String email, @PathVariable String id) {
		loginService.isExistAccount(email, id);

		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "/users")
	public ResponseEntity<ResponseMessage> findUserByToken(@CookieValue String accessToken) {
		return ResponseEntity.ok().body(loginService.findUserByToken(accessToken));
	}

	@GetMapping(value = "/logout/message")
	public ResponseEntity<ResponseMessage> logout() {
		return ResponseEntity.ok().body(ResponseMessage.of(LOGOUT_SUCCESS));
	}

}