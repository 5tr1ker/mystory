package noticeboard.repository.custom;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.QueryHints;

import noticeboard.entity.DTO.ReturnPostDataDTO;
import noticeboard.entity.freeboard.FreeAttach;
import noticeboard.entity.freeboard.FreePost;
import noticeboard.entity.freeboard.FreeWhoLike;

public interface CustomPostRepository {
	List<FreeWhoLike> getRecommender(Long postId, String userId);
	List<ReturnPostDataDTO> findPostBySearch(String postContent);
	List<ReturnPostDataDTO> findPostBySearchAndTag(String tagData);
	FreePost findPostByNumbers(Long postNumber);
	@QueryHints(value = @QueryHint(name = "org.hibernate.readOnly" ,value = "true"))
	List<FreeAttach> getAttachment(Long postId);
	List<FreeAttach> modifiedAttachment(Long postId);
}
