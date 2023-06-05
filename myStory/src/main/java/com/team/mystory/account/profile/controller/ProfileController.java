package com.team.mystory.account.profile.controller;

import java.util.Map;

import com.team.mystory.account.profile.dto.ProfileResponse;
import com.team.mystory.account.profile.dto.StatisticsResponse;
import com.team.mystory.common.ResponseMessage;
import com.team.mystory.security.jwt.service.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.team.mystory.account.profile.dto.ProfileRequest;
import com.team.mystory.account.profile.service.ProfileService;

import javax.security.auth.login.AccountException;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {

	private final ProfileService profileService;
	
	@GetMapping(value = "/statistics")
	public ResponseEntity<ResponseMessage> profileInfo(@CookieValue String accessToken) {
		return ResponseEntity.ok().body(profileService.getProfile(accessToken));
	}
	
	@PutMapping
	public ResponseEntity<ResponseMessage> profileUpdate(@RequestBody ProfileRequest profileRequest , @CookieValue String accessToken , HttpServletResponse response)
			throws AccountException {

		return ResponseEntity.ok().body(profileService.updateProfile(profileRequest , accessToken , response));
	}

	@GetMapping
	public ResponseEntity<ResponseMessage> getProfileData(@CookieValue String accessToken) throws AccountException {
		return ResponseEntity.ok().body(profileService.getProfileFromUser(accessToken));
	}
}
