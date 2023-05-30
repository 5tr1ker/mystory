package com.team.mystory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team.mystory.entity.DTO.ReturnPostDataDTO;
import com.team.mystory.entity.freeboard.FreeCommit;
import com.team.mystory.entity.freeboard.FreePost;
import com.team.mystory.repository.CommitRepository;
import com.team.mystory.repository.PostRepository;

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
