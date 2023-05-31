package com.team.mystory.post.tag.service;

import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team.mystory.post.tag.repository.TagRepository;

@Service
@Transactional
public class TagService {
	
	@Autowired TagRepository tag;
	
	public List<String> getAllTag() {
		return tag.findAllTagData();
	}
}
