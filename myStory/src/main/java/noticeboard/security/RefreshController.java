package noticeboard.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import noticeboard.security.service.JwtService;

@RestController
@Controller
public class RefreshController {

	@Autowired JwtService jwtService;
	
	@RequestMapping(value = "/refresh" , method = RequestMethod.POST)
	public Map<String , String> validateRefreshToken(@RequestBody HashMap<String, String> bodyJson) {
		
		Map<String, String> map = jwtService.validateRefreshToken(bodyJson.get("refreshToken"));
		if(map.get("status").equals("402")) {
			map.put("refreshApiResponseMessage", HttpStatus.UNAUTHORIZED.toString());
			return map;
		}
		
		map.put("refreshApiResponseMessage", HttpStatus.OK.toString());
		return map;
	}
}
