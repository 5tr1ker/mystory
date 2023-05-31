package com.team.mystory.post.post.repository;

import com.team.mystory.post.post.dto.ReturnPostDataDTO;
import com.team.mystory.post.post.domain.FreeAttach;
import com.team.mystory.post.post.domain.FreePost;
import com.team.mystory.post.post.domain.FreeWhoLike;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class PostRepositoryImpl extends QuerydslRepositorySupport implements CustomPostRepository {

	public PostRepositoryImpl() {
		super(FreePost.class);
	}
	
	@Override
	public List<FreeWhoLike> getRecommender(Long postId , String userId) {
//		QFreePost qfp = QFreePost.freePost;
//		QFreeWhoLike qhl = QFreeWhoLike.freeWhoLike;
//
//		JPQLQuery query = from(qfp);
//
//		return query.join(qfp.freeWhoLike , qhl).where(qhl.recommender.in(userId).and(qfp.numbers.eq(postId))).list(qhl);
		return null;
	}

	@Override
	public List<ReturnPostDataDTO> findPostBySearch(String postContent) {
//		QFreePost qfp = QFreePost.freePost;
//
//		JPQLQuery query = from(qfp);
//		return query.where(qfp.content.contains(postContent).or(qfp.title.contains(postContent))).list(Projections.constructor(
//				ReturnPostDataDTO.class	, qfp.numbers , qfp.title , qfp.writer , qfp.postTime , qfp.likes , qfp.views ));
		return null;
	}

	@Override
	public List<ReturnPostDataDTO> findPostBySearchAndTag(String tagData) {
//		QFreeTag qfm = QFreeTag.freeTag;
//		QFreePost qfp = QFreePost.freePost;
//
//		JPQLQuery query = from(qfm);
//		return query.join(qfm.freePost , qfp).where(qfm.tagData.eq(tagData)).list(Projections.constructor(
//				ReturnPostDataDTO.class	, qfp.numbers , qfp.title , qfp.writer , qfp.postTime , qfp.likes , qfp.views )) ;
		return null;
	}
	
	@Override
	public FreePost findPostByNumbers(Long postNumber) {
//		QFreePost fp = QFreePost.freePost;
//
//		JPQLQuery query = from(fp);
//		return query.where(fp.numbers.eq(postNumber)).uniqueResult(fp);
		return null;
	}
	
	@Override
	public List<FreeAttach> getAttachment(Long postId) {
//		QFreePost qfp = QFreePost.freePost;
//
//		JPQLQuery query = from(qfp);
//		FreePost result = query.where(qfp.numbers.eq(postId)).join(qfp.freeAttach).fetch().uniqueResult(qfp);
//		if (result == null) return null;
//		return result.getFreeAttach();
		return null;
	}

	@Override
	public List<FreeAttach> modifiedAttachment(Long postId) {
//		QFreePost qfp = QFreePost.freePost;
//
//		JPQLQuery query = from(qfp);
//		FreePost result = query.where(qfp.numbers.eq(postId)).join(qfp.freeAttach).fetch().uniqueResult(qfp);
//		if (result == null) return null;
//		return result.getFreeAttach();
		return null;
	}
	
}
