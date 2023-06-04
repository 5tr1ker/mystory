package com.team.mystory.account.user.controller;

import com.team.mystory.account.user.dto.LoginRequest;
import com.team.mystory.account.user.service.LoginService;
import com.team.mystory.common.ResponseMessage;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountException;

import static com.team.mystory.common.ResponseCode.LOGOUT_SUCCESS;

@RestController
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;

	@PostMapping(value = "/registers")
	public ResponseEntity<ResponseMessage> register(@RequestBody LoginRequest loginRequest) throws AccountException {
		return ResponseEntity.ok().body(loginService.register(loginRequest));
	}

	@PostMapping(value = "/logins")
	public ResponseEntity login(@RequestBody LoginRequest userInfo , HttpServletResponse response) throws AccountException {
		return ResponseEntity.ok().body(loginService.login(userInfo , response));
	}

	@DeleteMapping(value = "/users")
	public ResponseEntity<ResponseMessage> deleteUser(@CookieValue String accessToken) throws AccountException {
		loginService.removeUser(accessToken);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/users/{idInfo}")
	public ResponseEntity idCheck(@PathVariable("idInfo") String userInfo) throws AccountException {
		return ResponseEntity.ok().body(loginService.getUserByUserId(userInfo));
	}

	@GetMapping(value = "/logout/message")
	public ResponseEntity logout() {
		return ResponseEntity.ok().body(ResponseMessage.of(LOGOUT_SUCCESS));
	}
}
