package com.team.mystory.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.team.mystory.entity.userdata.IdInfo;
import com.team.mystory.entity.userdata.ProfileSetting;
import com.team.mystory.repository.LoginRepository;
import com.team.mystory.security.JwtTokenProvider;
import com.team.mystory.security.Token;
import com.team.mystory.security.oauth.CreateOAuthUser;
import com.team.mystory.security.oauth.CustomOAuth2UserService;
import com.team.mystory.security.service.JwtService;
import com.team.mystory.service.LoginService;

@RestController
@Controller
public class LoginController {

	@Autowired
	LoginRepository loginRepos;
	@Autowired
	LoginService login;
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	@Autowired
	JwtService jwtService;
	@Autowired
	CreateOAuthUser createOauthUser;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
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
	
	@RequestMapping(value = "/googleLogin", produces = "application/json", method = RequestMethod.GET)
	public Token googleLogin(@RequestParam("code") String code, HttpServletResponse response) {
		JsonNode accessToken;
		Token result = null;
		
		JsonNode jsonToken = CustomOAuth2UserService.getGoogleAccessToken(code); // 카카오 로그인 처리
		accessToken = jsonToken.get("access_token");

		result = createOauthUser.createGoogleUser(accessToken.toString().replace("\"", ""));
		System.out.println(accessToken.toString());
		return result;
	}

	@RequestMapping(value = "/kakaoLogin", produces = "application/json", method = RequestMethod.GET)
	public Token kakaoLogin(@RequestParam("code") String code, HttpServletResponse response) {
		JsonNode accessToken;
		Token result = null;

		JsonNode jsonToken = CustomOAuth2UserService.getKakaoAccessToken(code); // 카카오 로그인 처리
		accessToken = jsonToken.get("access_token");

		result = createOauthUser.createKakaoUser(accessToken.toString().replace("\"", ""));

		return result;
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
