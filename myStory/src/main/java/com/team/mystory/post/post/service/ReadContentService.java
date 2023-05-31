package com.team.mystory.post.post.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team.mystory.post.post.dto.ReturnPostDataDTO;
import com.team.mystory.post.comment.domain.FreeCommit;
import com.team.mystory.post.post.domain.FreePost;
import com.team.mystory.post.comment.repository.CommitRepository;
import com.team.mystory.post.post.repository.PostRepository;

@Service
@Transactional(readOnly = true)
public class ReadContentService {

	@Autowired PostRepository post;
	@Autowired CommitRepository commit;
	
	public List<FreeCommit> getCommit(Long postId) {
		return post.getCommit(postId);
	}
	
	public FreePost getPostData(Long postId) {
		return post.getViewPostData(postId);
	}
	public List<ReturnPostDataDTO> getFreePost(Pageable pageable) {
		return post.getPostData(pageable);
	}

}
