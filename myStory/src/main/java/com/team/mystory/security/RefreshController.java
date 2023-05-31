package com.team.mystory.security;

import java.util.Map;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.team.mystory.security.service.JwtService;

@RestController
@Controller
public class RefreshController {

	@Autowired JwtService jwtService;
	
	@RequestMapping(value = "/expireAccess")
	public String expireAccess(@RequestHeader(value="Authorization") String authorization , HttpServletResponse response) {
		return "AccessTokenExpire";
	}
	
	@RequestMapping(value = "/refresh" , method = RequestMethod.GET)
	public Map<String , String> validateRefreshToken(@CookieValue(name = "refreshToken") String cookie) {
		
		Map<String, String> map = jwtService.validateRefreshToken(cookie);
		if(map.get("status").equals("402")) {
			map.put("refreshApiResponseMessage", HttpStatus.UNAUTHORIZED.toString());
			return map;
		}
		
		map.put("refreshApiResponseMessage", HttpStatus.OK.toString());
		return map;
	}
}
