package com.team.mystory.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.team.mystory.entity.DTO.ProfileDTO;
import com.team.mystory.service.ProfileService;

@RestController
@Controller
public class ProfileController {

	@Autowired ProfileService profileServ;
	
	@RequestMapping(value = "/auth/profile/{idInfo}" , method = RequestMethod.GET )
	public Map<String , String> profileInfo(@PathVariable("idInfo") String idInfo) {
		return profileServ.getProfile(idInfo);
	}
	
	@RequestMapping(value = "/auth/profile" , method = RequestMethod.PATCH)
	public int profileUpdate(@RequestBody ProfileDTO data) {
		return profileServ.updateProfileData(data);
	}
}
