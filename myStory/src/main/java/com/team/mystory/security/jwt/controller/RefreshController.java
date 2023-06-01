package com.team.mystory.security.jwt.controller;

import com.team.mystory.security.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class RefreshController {

	private final JwtService jwtService;

	@PostMapping(value = "/refresh")
	public ResponseEntity validateRefreshToken(@CookieValue(name = "refreshToken") String cookie) {
		return ResponseEntity.ok().body(jwtService.validateRefreshToken(cookie));
	}
}
