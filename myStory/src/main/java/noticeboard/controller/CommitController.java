package noticeboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import noticeboard.entity.freeboard.FreeCommit;
import noticeboard.repository.CommitRepository;
import noticeboard.service.PostService;
import noticeboard.service.ReadContentService;

@RestController
@Controller
public class CommitController {

	@Autowired ReadContentService commit;
	@Autowired CommitRepository commitRepos;
	@Autowired PostService postServ;
	@Autowired ReadContentService readContent;
	
	@RequestMapping(value = "/getCommit/{postId}" , method = RequestMethod.GET )
	public List<FreeCommit> getCommit(@PathVariable("postId") Long postId) {
		return readContent.getCommit(postId);
	}
	
	@RequestMapping(value = "/auth/deleteCommit/{commitId}" , method = RequestMethod.DELETE)
	public void deleteCommit(@PathVariable("commitId") Long commitId) {
		commitRepos.delete(commitId);
	}
	
	@RequestMapping(value = "/auth/deleteAllCommit/{idInfo}" , method = RequestMethod.DELETE)
	public void deleteAllCommit(@PathVariable("idInfo") String idInfo ) {
		postServ.deleteAllCommit(idInfo);
	}
	
	@RequestMapping(value = "/getNotifice/{idInfo}" , method = RequestMethod.GET)
	public List<FreeCommit> getNotifice(@PathVariable("idInfo") String idInfo) {
		return commitRepos.getNotifice(idInfo);
	}
}
