package noticeboard.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import noticeboard.entity.DTO.PostdataDTO;
import noticeboard.entity.freeboard.FreeCommit;
import noticeboard.entity.freeboard.FreePost;
import noticeboard.entity.freeboard.FreeTag;
import noticeboard.entity.freeboard.FreeWhoLike;
import noticeboard.entity.userdata.IdInfo;
import noticeboard.repository.CommitRepository;
import noticeboard.repository.LoginRepository;
import noticeboard.repository.PostRepository;
import noticeboard.repository.TagRepository;

@Service
@Transactional
public class PostService {

	@Autowired PostRepository writting;
	@Autowired LoginRepository login;
	@Autowired TagRepository tag;
	@Autowired CommitRepository commit;
	
	public HashMap<String, Long> writePost(PostdataDTO data) {
		HashMap<String, Long> result = new HashMap<String, Long>();
		
		Long number = writting.getPostNumber();
		FreePost fp = new FreePost();
		fp.setContent(data.getPostContent().getContent());
		fp.setTitle(data.getPostContent().getTitle());
		fp.setPrivates(data.getPostOption().isPrivates());
		fp.setBlockComm(data.getPostOption().isBlockComm());
		if(number == null) fp.setNumbers(1);
		else fp.setNumbers(number + 1);
		
		result.put("postNumber" , fp.getNumbers());
		IdInfo idInfo = login.findById(data.getIdStatus()); // 사용자 아이디
		fp.setWriter(idInfo.getId()); // 작성자
		fp.setIdinfo(idInfo); // 편의 메소드에 접근
		
		String tagData[] = data.getLabelData(); // 태그 데이터
		for(String str : tagData) {
			FreeTag ft = new FreeTag(str);
			fp.addTagData(ft);
			ft.getFreePost().add(fp);
		}
		try {
			writting.save(fp);
			idInfo.addFreePost(fp);
			login.save(idInfo);
			result.put("result" , 0L);
			return result;
		} catch(Exception e) {
			result.put("result" , -1L);
			return result;
		}
	}
	
	public int addCommit(String content , String writer , Long postId , Long postNumber , String postType) {
		try {
			FreeCommit fc = FreeCommit.createCommitData(content, writer , postNumber , postType); // 댓글 내용
			FreePost fp = writting.findPostByNumbers(postId);
			fp.addFreeCommit(fc);
		
			// writting.save(fp); 이미 영속화를 저장하면 fp가 2번 저장됨
		} catch (Exception e) {
			System.out.println("Service : postService 에서 오류가 발생했습니다. : " + e);
			return -1;
		}
		return 0;
	}
	
	public int deletePost(Long postId) {
		try {
			writting.deleteByNumbers(postId);
		} catch (Exception e) {
			return -1;
		}
		return 0;
	}
	
	public int updateLike(Long postId , String userName) {
		if(writting.getRecommender(postId , userName).isEmpty()) {
			FreePost fp = writting.findPostByNumbers(postId);
			fp.setLikes(fp.getLikes() + 1); // 추천 수 증가
			
			FreeWhoLike wfl = FreeWhoLike.makefreeWhoLike(userName, fp);
			fp.addWhoLike(wfl);
			
			return 0;
		} else {
			return -2;
		}
	}
	
	public int modifiedPost(Long postId , PostdataDTO postData) {
		FreePost fp = writting.findPostByNumbers(postId);
		
		try {
			fp.setContent(postData.getPostContent().getContent());
			fp.setTitle(postData.getPostContent().getTitle());
			fp.setBlockComm(postData.getPostOption().isBlockComm());
			fp.setPrivates(postData.getPostOption().isPrivates());
			
			fp.getFreeTag().clear();
			String TagData[] = postData.getLabelData();
			for(String data : TagData) {
				FreeTag ft = new FreeTag(data);
				fp.addTagData(ft);
			}
		} catch (Exception e) {
			System.out.println("postService modifiedPost 에서 오류가 발생했습니다. " + e);
			return -1;
		}
		return 0;
	}
	
	public void updateView(Long postId) {
		writting.updatePostViews(postId);
	}
	
	public void deleteAllCommit(String idInfo) {
		commit.deleteAllCommit(idInfo);
	}

}
