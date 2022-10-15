package noticeboard.controller;

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

import noticeboard.entity.userdata.IdInfo;
import noticeboard.entity.userdata.ProfileSetting;
import noticeboard.repository.LoginRepository;
import noticeboard.security.JwtTokenProvider;
import noticeboard.security.Token;
import noticeboard.security.oauth.CreateOAuthUser;
import noticeboard.security.oauth.CustomOAuth2UserService;
import noticeboard.security.service.JwtService;
import noticeboard.service.LoginService;

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

	@RequestMapping(value = "/auth/idDelete/{idInfo}", method = RequestMethod.DELETE)
	public int idDelete(@PathVariable("idInfo") String userInfo) {
		int result = login.remove(userInfo);
		return result;
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
		
		Cookie cookie = new Cookie("refreshToken", result.getId());
		cookie.setPath("/");
		cookie.setMaxAge(1209600);
		cookie.setSecure(true);
		cookie.setHttpOnly(true);
		response.addCookie(cookie);

		Token tokenDTO = jwtTokenProvider.createAccessToken(result.getUsername(), result.getRoles());
		jwtService.login(tokenDTO);
		return tokenDTO;
	}

	@RequestMapping(value = "/findId", method = RequestMethod.POST)
	public String findId(@RequestBody Map<String, String> userInfo) {
		String result = login.findId(userInfo);
		System.out.println(result);
		return result;
	}

	@RequestMapping(value = "/auth/getProfileData/{id}", method = RequestMethod.GET)
	public ProfileSetting getProfileData(@PathVariable("id") String id) {
		ProfileSetting ps = login.getProfile(id);
		return ps;
	}
}
