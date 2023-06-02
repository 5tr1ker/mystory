package com.team.mystory.post.post.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.mystory.account.user.domain.User;
import com.team.mystory.post.post.domain.Post;
import com.team.mystory.post.post.dto.PostListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.team.mystory.account.user.domain.QUser.user;
import static com.team.mystory.post.comment.domain.QFreeCommit.freeCommit;
import static com.team.mystory.post.post.domain.QPost.post;
import static com.team.mystory.post.tag.domain.QFreeTag.freeTag;


@RequiredArgsConstructor
public class PostRepositoryImpl implements CustomPostRepository {

	private final JPAQueryFactory queryFactory;
	
	@Override
	public Optional<User> findRecommendationFromPost(Long postId , String userId) {
		User result = queryFactory.select(user)
				.from(post)
				.innerJoin(post.writer).on(user.id.eq(userId))
				.where(post.postId.eq(postId))
				.fetchOne();

		return Optional.ofNullable(result);
	}

	@Override
	public List<PostListResponse> findPostBySearch(String content) {
//		Qpost qfp = Qpost.post;
//
//		JPQLQuery query = from(qfp);
//		return query.where(qfp.content.contains(postContent).or(qfp.title.contains(postContent)))
//		.list(Projections.constructor(
//				PostListResponse.class	, qfp.numbers , qfp.title , qfp.writer , qfp.postDate , qfp.likes , qfp.views ));
		return queryFactory.select(Projections.constructor(PostListResponse.class , post.postId , post.title
				, user.id , post.postDate , post.likes , post.views))
				.from(post)
				.innerJoin(post.writer)
				.where(post.content.contains(content).or(post.content.contains(content)))
				.fetch();
	}

	@Override
	public List<PostListResponse> findPostByTag(String tag) {
//		QFreeTag qfm = QFreeTag.freeTag;
//		Qpost qfp = Qpost.post;
//
//		JPQLQuery query = from(qfm);
//		return query.join(qfm.post , qfp).where(qfm.tagData.eq(tagData)).list(Projections.constructor(
//				PostListResponse.class	, qfp.numbers , qfp.title , qfp.writer , qfp.postDate , qfp.likes , qfp.views )) ;
		return queryFactory.select(Projections.constructor(PostListResponse.class , post.postId , post.title ,
						user.id , post.postDate , post.likes , post.views))
				.from(post)
				.innerJoin(post.writer)
				.innerJoin(post.freeTag).on(freeTag.tagData.eq(tag))
				.fetch();
	}

	@Override
	public Optional<Post> findPostByPostId(long postId) {
		Post result = queryFactory.select(post)
				.from(post)
				.where(post.postId.eq(postId))
				.fetchOne();

		return Optional.ofNullable(result);
	}

	// SELECT new com.team.mystory.post.post.dto.PostListResponse(f.number , f.title , f.writer
	// , f.postDate , f.likes , f.views , COUNT(c))
	// FROM Post f LEFT OUTER JOIN f.freeCommit c group by f.number order by f.number DESC
	@Override
	public List<PostListResponse> getPostList(Pageable pageable) {
		return queryFactory.select(Projections.constructor(PostListResponse.class , post.postId
						, post.title , user.id , post.postDate , post.likes
						, post.views , freeCommit.count()))
				.from(post)
				.innerJoin(post.writer)
				.leftJoin(post.freeCommit)
				.groupBy(post.postId)
				.orderBy(post.postId.desc())
				.fetch();
	}

	@Override
	public long getTotalNumberOfPosts() {
		return queryFactory.select(post.count())
				.from(post)
				.fetchOne();
	}

	// @Query("SELECT f.tagData from Post p join p.freeTag f where p.number = :postId")
	@Override
	public List<String> findTagsInPostId(long postId) {
		return queryFactory.select(freeTag.tagData)
				.from(post)
				.innerJoin(post.freeTag)
				.where(post.postId.eq(postId))
				.fetch();
	}

	// @Modifying(clearAutomatically = true)
	// UPDATE Post p set p.views = p.views + 1 where p.number = :postId
	@Override
	public void updatePostView(long postId) {
		queryFactory.update(post)
				.set(post.views , post.views.add(1))
				.where(post.postId.eq(postId))
				.execute();
	}

}
