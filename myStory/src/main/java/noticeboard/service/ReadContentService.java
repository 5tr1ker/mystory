package noticeboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import noticeboard.entity.DTO.ReturnPostDataDTO;
import noticeboard.entity.freeboard.FreeCommit;
import noticeboard.entity.freeboard.FreePost;
import noticeboard.repository.CommitRepository;
import noticeboard.repository.PostRepository;

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
