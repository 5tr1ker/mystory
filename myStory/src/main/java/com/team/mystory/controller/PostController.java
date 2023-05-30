package com.team.mystory.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.ByteStreams;

import com.team.mystory.entity.DTO.PostdataDTO;
import com.team.mystory.entity.DTO.ReturnPostDataDTO;
import com.team.mystory.repository.PostRepository;
import com.team.mystory.service.AttachManager;
import com.team.mystory.service.PostService;
import com.team.mystory.service.ReadContentService;

@RestController
@Controller
public class PostController {
	
	@Autowired PostService writtingService;
	@Autowired PostRepository postRepos;
	@Autowired AttachManager attachManager;
	@Autowired ReadContentService commitService;
	
	@RequestMapping(value = "/auth/newPost" , method = RequestMethod.POST)
	public HashMap<String, Long> newPost(@RequestBody PostdataDTO postData) {
		return writtingService.writePost(postData);
	}

	@RequestMapping(value = "/totalPostDataCount" , method = RequestMethod.GET)
	public int totalPostDataCount() {
		return postRepos.getTotalPostDataCount();
	}
	
	@RequestMapping(value = "/auth/uploadData/{postNumber}" , method = RequestMethod.POST)
	public void fileUpload(@RequestPart(value = "file" , required = true) List<MultipartFile> file , @PathVariable("postNumber")Long postNumber) {
		attachManager.fileUpload(file , postNumber);
	}
	
	@RequestMapping(value = "/post" , method = RequestMethod.GET)
	public List<ReturnPostDataDTO> getFreePost(Pageable pageable) {
		return commitService.getFreePost(pageable);
	}
	
	@RequestMapping(value = "/selectPost/{postId}" , method = RequestMethod.GET)
	public HashMap<String, Object> selectPost(@PathVariable("postId") Long postId) {
		HashMap<String, Object> hash = new HashMap<String, Object>();
		hash.put("postData", commitService.getPostData(postId)); // Post정보
		hash.put("tagData", postRepos.findPostTag(postId)); // Tag 정보
		hash.put("attachment", postRepos.getAttachment(postId)); // Attach 정보
		writtingService.updateView(postId);
		return hash;
	}
	
	@RequestMapping(value = "/auth/modifiedPost/{postId}" , method = RequestMethod.GET)
	public HashMap<String, Object> modifiedPost(@PathVariable("postId") Long postId) {
		HashMap<String, Object> hash = new HashMap<String, Object>();
		hash.put("postData", postRepos.getViewPostData(postId)); // Post정보
		hash.put("tagData", postRepos.findPostTag(postId)); // Tag 정보
		hash.put("attachment", postRepos.modifiedAttachment(postId)); // Attach 정보
		return hash;
	}
	
	@RequestMapping(value = "/auth/likes/{postId}" , method = RequestMethod.PATCH)
	public int updateLike(@PathVariable("postId") Long postId , @RequestBody Map<String , String> userId) {
		return writtingService.updateLike(postId, userId.get("idStatus"));
	}
	
	@RequestMapping(value = "/auth/post/{postId}" , method = RequestMethod.DELETE)
	public int deletePost(@PathVariable("postId") Long postId) {
		return writtingService.deletePost(postId);
	}
	
	@RequestMapping(value = "/auth/modifiedPost/{postId}" , method = RequestMethod.PATCH)
	public int modifiedPost(@PathVariable("postId") Long postId , @RequestBody PostdataDTO postData ) {
		attachManager.modifiedUpload(postData.getDeletedFileList() , postId);
		return writtingService.modifiedPost(postId, postData);
	}
	
	@RequestMapping(value = "/findPostBySearch/{postContent}" , method = RequestMethod.GET)
	public List<ReturnPostDataDTO> findPostBySearch(@PathVariable("postContent") String postContent) {
		return postRepos.findPostBySearch(postContent);
	}
	
	@RequestMapping(value = "/findPostBySearchAndTag/{tagData}" , method = RequestMethod.GET)
	public List<ReturnPostDataDTO> findPostBySearchAndTag(@PathVariable("tagData") String tagData) {
		return postRepos.findPostBySearchAndTag(tagData);
	}
	
	@RequestMapping(value = "/onDownload/{fileName}" , method = RequestMethod.GET)
	public void onDownload(@PathVariable("fileName") String fileName , HttpServletResponse response) throws IOException {
		// String filePath = "C:\\Users\\tjseo\\OneDrive\\바탕 화면\\study\\noticeBoardClient\\upload\\";
		String realName = postRepos.getFileName(fileName);
		String extend = realName.substring(realName.lastIndexOf('.') , realName.length());
		
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ";");
		response.setContentType("text/plain");
		
		File downFile = new File(fileName + extend); //파일 다운로드
	    FileInputStream fileIn = new FileInputStream(downFile); //파일 읽어오기
	    ByteStreams.copy(fileIn, response.getOutputStream());
	    response.flushBuffer();
	}
}
