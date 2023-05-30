package com.team.mystory.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.team.mystory.entity.freeboard.FreeCommit;
import com.team.mystory.repository.CommitRepository;
import com.team.mystory.service.PostService;
import com.team.mystory.service.ReadContentService;

@RestController
@Controller
public class CommitController {

	@Autowired ReadContentService commit;
	@Autowired CommitRepository commitRepos;
	@Autowired PostService postServ;
	@Autowired ReadContentService readContent;
	
	@RequestMapping(value = "/commit/{postId}" , method = RequestMethod.GET )
	public List<FreeCommit> getCommit(@PathVariable("postId") Long postId) {
		return readContent.getCommit(postId);
	}
	
	@RequestMapping(value = "/auth/commit" , method = RequestMethod.POST)
	public int addCommit(@RequestBody Map<String , String> postData) {
		return postServ.addCommit(postData.get("data") , postData.get("writter") , Long.parseLong(postData.get("postNum")) , Long.parseLong(postData.get("postNumber")) , postData.get("postType") );
	}
	
	@RequestMapping(value = "/auth/commit/{commitId}" , method = RequestMethod.DELETE)
	public void deleteCommit(@PathVariable("commitId") Long commitId) {
		commitRepos.delete(commitId);
	}
	
	@RequestMapping(value = "/auth/allCommit/{idInfo}" , method = RequestMethod.DELETE)
	public void deleteAllCommit(@PathVariable("idInfo") String idInfo ) {
		postServ.deleteAllCommit(idInfo);
	}
	
	@RequestMapping(value = "/notifice/{idInfo}" , method = RequestMethod.GET)
	public List<FreeCommit> getNotifice(@PathVariable("idInfo") String idInfo) {
		return commitRepos.getNotifice(idInfo);
	}
}
