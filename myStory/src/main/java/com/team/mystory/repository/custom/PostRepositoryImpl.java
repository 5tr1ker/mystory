package com.team.mystory.repository.custom;

import java.util.List;

import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.Projections;

import com.team.mystory.entity.DTO.ReturnPostDataDTO;
import com.team.mystory.entity.freeboard.FreeAttach;
import com.team.mystory.entity.freeboard.FreePost;
import com.team.mystory.entity.freeboard.FreeWhoLike;
import com.team.mystory.entity.freeboard.QFreePost;
import com.team.mystory.entity.freeboard.QFreeTag;
import com.team.mystory.entity.freeboard.QFreeWhoLike;

public class PostRepositoryImpl extends QueryDslRepositorySupport implements CustomPostRepository {

	public PostRepositoryImpl() {
		super(FreePost.class);
	}
	
	@Override
	public List<FreeWhoLike> getRecommender(Long postId , String userId) {
		QFreePost qfp = QFreePost.freePost;
		QFreeWhoLike qhl = QFreeWhoLike.freeWhoLike;
		
		JPQLQuery query = from(qfp);
		
		return query.join(qfp.freeWhoLike , qhl).where(qhl.recommender.in(userId).and(qfp.numbers.eq(postId))).list(qhl);
	}

	@Override
	public List<ReturnPostDataDTO> findPostBySearch(String postContent) {
		QFreePost qfp = QFreePost.freePost;
		
		JPQLQuery query = from(qfp);
		return query.where(qfp.content.contains(postContent).or(qfp.title.contains(postContent))).list(Projections.constructor(
				ReturnPostDataDTO.class	, qfp.numbers , qfp.title , qfp.writer , qfp.postTime , qfp.likes , qfp.views ));
	}

	@Override
	public List<ReturnPostDataDTO> findPostBySearchAndTag(String tagData) {
		QFreeTag qfm = QFreeTag.freeTag;
		QFreePost qfp = QFreePost.freePost;
		
		JPQLQuery query = from(qfm);
		return query.join(qfm.freePost , qfp).where(qfm.tagData.eq(tagData)).list(Projections.constructor(
				ReturnPostDataDTO.class	, qfp.numbers , qfp.title , qfp.writer , qfp.postTime , qfp.likes , qfp.views )) ;
	}
	
	@Override
	public FreePost findPostByNumbers(Long postNumber) {
		QFreePost fp = QFreePost.freePost;
		
		JPQLQuery query = from(fp);
		return query.where(fp.numbers.eq(postNumber)).uniqueResult(fp);
	}
	
	@Override
	public List<FreeAttach> getAttachment(Long postId) {
		QFreePost qfp = QFreePost.freePost;
		
		JPQLQuery query = from(qfp);
		FreePost result = query.where(qfp.numbers.eq(postId)).join(qfp.freeAttach).fetch().uniqueResult(qfp);
		if (result == null) return null;
		return result.getFreeAttach();
	}

	@Override
	public List<FreeAttach> modifiedAttachment(Long postId) {
		QFreePost qfp = QFreePost.freePost;
		
		JPQLQuery query = from(qfp);
		FreePost result = query.where(qfp.numbers.eq(postId)).join(qfp.freeAttach).fetch().uniqueResult(qfp);
		if (result == null) return null;
		return result.getFreeAttach();
	}
	
}
