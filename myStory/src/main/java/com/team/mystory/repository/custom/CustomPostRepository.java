package com.team.mystory.repository.custom;

import java.util.List;

import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.QueryHints;

import com.team.mystory.entity.DTO.ReturnPostDataDTO;
import com.team.mystory.entity.freeboard.FreeAttach;
import com.team.mystory.entity.freeboard.FreePost;
import com.team.mystory.entity.freeboard.FreeWhoLike;

public interface CustomPostRepository {
	List<FreeWhoLike> getRecommender(Long postId, String userId);
	List<ReturnPostDataDTO> findPostBySearch(String postContent);
	List<ReturnPostDataDTO> findPostBySearchAndTag(String tagData);
	FreePost findPostByNumbers(Long postNumber);
	@QueryHints(value = @QueryHint(name = "org.hibernate.readOnly" ,value = "true"))
	List<FreeAttach> getAttachment(Long postId);
	List<FreeAttach> modifiedAttachment(Long postId);
}
