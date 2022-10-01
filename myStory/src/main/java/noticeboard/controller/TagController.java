package noticeboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import noticeboard.service.TagService;

@RestController
@Controller
public class TagController {
	
	@Autowired TagService tagService;
	
	@RequestMapping(value="/getTag" , method = RequestMethod.GET)
	public List<String> getTag() {
		return tagService.getAllTag();
	}
}
