package com.team.mystory.post.comment.controller;

import java.util.List;
import java.util.Map;

import com.team.mystory.post.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.team.mystory.post.comment.domain.FreeCommit;
import com.team.mystory.post.comment.repository.CommitRepository;
import com.team.mystory.post.post.service.PostService;

@RestController
@Controller
public class CommitController {

	@Autowired CommitRepository commitRepos;
	@Autowired PostService postService;
	@Autowired CommentService commentService;
	
	@RequestMapping(value = "/commit/{postId}" , method = RequestMethod.GET )
	public List<FreeCommit> getCommit(@PathVariable("postId") Long postId) {
		return commentService.getCommit(postId);
	}
	
	@RequestMapping(value = "/auth/commit" , method = RequestMethod.POST)
	public int addCommit(@RequestBody Map<String , String> postData) {
		return commentService.addComment(postData.get("data") , postData.get("writter") , Long.parseLong(postData.get("postNum")) , Long.parseLong(postData.get("postNumber")) , postData.get("postType") );
	}
	
	@RequestMapping(value = "/auth/commit/{commitId}" , method = RequestMethod.DELETE)
	public void deleteCommit(@PathVariable("commitId") Long commitId) {
		commitRepos.deleteById(commitId);
	}
	
	@RequestMapping(value = "/auth/allCommit/{idInfo}" , method = RequestMethod.DELETE)
	public void deleteAllCommit(@PathVariable("idInfo") String idInfo ) {
		postService.deleteAllCommit(idInfo);
	}
	
	@RequestMapping(value = "/notifice/{idInfo}" , method = RequestMethod.GET)
	public List<FreeCommit> getNotifice(@PathVariable("idInfo") String idInfo) {
		return commitRepos.getNotifice(idInfo);
	}
}
