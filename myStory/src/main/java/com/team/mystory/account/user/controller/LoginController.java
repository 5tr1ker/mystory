package com.team.mystory.account.user.controller;

import com.team.mystory.account.profile.domain.ProfileSetting;
import com.team.mystory.account.user.domain.IdInfo;
import com.team.mystory.account.user.repository.LoginRepository;
import com.team.mystory.account.user.service.LoginService;
import com.team.mystory.security.jwt.dto.Token;
import com.team.mystory.security.jwt.service.JwtService;
import com.team.mystory.security.jwt.support.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class LoginController {

	private final LoginRepository loginRepos;
	private final LoginService login;
	private final JwtTokenProvider jwtTokenProvider;
	private final JwtService jwtService;

	@PostMapping(value = "/register")
	public int register(@RequestBody Map<String, String> userInfo) {
		int result = login.register(userInfo);
		return result;
	}

	@RequestMapping(value = "/auth/user/{idInfo}", method = RequestMethod.DELETE)
	public int idDelete(@PathVariable("idInfo") String userInfo) {
		int result = login.remove(userInfo);
		return result;
	}
	
	@RequestMapping(value = "/idCheck/{idInfo}", method = RequestMethod.GET)
	public int idCheck(@PathVariable("idInfo") String userInfo) {
		IdInfo result = loginRepos.findById(userInfo);
		if(result == null) return 1;
		else return -1;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Token login(@RequestBody Map<String, String> userInfo , HttpServletResponse response) {
		IdInfo result = login.login(userInfo);
		if (result == null) {
			return null;
		}
		

		Token tokenDTO = jwtTokenProvider.createAccessToken(result.getUsername(), result.getRoles());
		jwtService.login(tokenDTO);
		Cookie cookie = new Cookie("refreshToken", tokenDTO.getRefreshToken());
		cookie.setPath("/");
		cookie.setMaxAge(1209600);
		cookie.setSecure(true);
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
		return tokenDTO;
	}

	@RequestMapping(value = "/findId", method = RequestMethod.GET)
	public String findId(@RequestParam("id") String userInfo) {
		Map<String , String> map = new HashMap<String, String>();
		map.put("id", userInfo);
		String result = login.findId(map);
		return result;
	}

	@RequestMapping(value = "/auth/profileData/{id}", method = RequestMethod.GET)
	public ProfileSetting getProfileData(@PathVariable("id") String id) {
		ProfileSetting ps = login.getProfile(id);
		return ps;
	}
}
