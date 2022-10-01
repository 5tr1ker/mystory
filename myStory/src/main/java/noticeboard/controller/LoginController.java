package noticeboard.controller;

import java.util.Map;

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
import noticeboard.security.oauth.KakaoOAuth2UserService;
import noticeboard.security.service.JwtService;
import noticeboard.service.LoginService;


@RestController
@Controller
public class LoginController {

	@Autowired LoginRepository loginRepos;
	@Autowired LoginService login;
	@Autowired JwtTokenProvider jwtTokenProvider;
	@Autowired JwtService jwtService;
	
	@RequestMapping(value = "/register" , method = RequestMethod.POST)
	public int register(@RequestBody Map<String, String> userInfo) {
		int result = login.register(userInfo);
		return result;
	}
	
	@RequestMapping(value = "/auth/idDelete/{idInfo}" , method = RequestMethod.DELETE)
	public int idDelete(@PathVariable("idInfo") String userInfo) {
		int result = login.remove(userInfo);
		return result;
	}
	
	@RequestMapping(value = "/oauth2/code/kakao" , produces = "application/json" ,  method = RequestMethod.GET)
	public String kakaoLogin(@RequestParam("code") String code) {
		JsonNode accessToken;

		JsonNode jsonToken = KakaoOAuth2UserService.getKakaoAccessToken(code); // 해당 요청을 가져온다.
		accessToken = jsonToken.get("access_token");
		System.out.println("결과값 : " + accessToken);
		return "테스트 성공";
	}
	
	@RequestMapping(value = "/login" , method = RequestMethod.POST)
	public Token login(@RequestBody Map<String , String> userInfo) {
		IdInfo result = login.login(userInfo);
		if(result == null) {
			new IllegalArgumentException("가입되지 않은 ID");
		}
		
		Token tokenDTO = jwtTokenProvider.createAccessToken(result.getUsername(), result.getRoles());
		jwtService.login(tokenDTO);
		return tokenDTO;
	}
	
	@RequestMapping(value = "/findId" , method = RequestMethod.POST)
	public String findId(@RequestBody Map<String , String> userInfo) {
		String result = login.findId(userInfo);
		System.out.println(result);
		return result;
	}
	
	@RequestMapping(value = "/auth/getProfileData/{id}" , method = RequestMethod.GET)
	public ProfileSetting getProfileData(@PathVariable("id") String id) {
		ProfileSetting ps = login.getProfile(id);
		return ps;
	}
}
