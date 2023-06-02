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
				.innerJoin(post.recommendation , user).on(user.id.eq(userId))
				.where(post.postId.eq(postId))
				.fetchOne();

		return Optional.ofNullable(result);
	}

	@Override
	public List<PostListResponse> findPostBySearch(Pageable pageable , String content) {
		return queryFactory.select(Projections.constructor(PostListResponse.class , post.postId , post.title
				, user.id , post.postDate , post.likes , post.views , freeCommit.count()))
				.from(post)
				.innerJoin(post.writer , user).on(post.writer.eq(user))
				.leftJoin(post.freeCommit , freeCommit).on(freeCommit.post.eq(post))
				.where(post.content.contains(content).or(post.title.contains(content)))
				.groupBy(post.postId , post.title , user)
				.orderBy(post.postId.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
	}

	@Override
	public List<PostListResponse> findPostByTag(Pageable pageable , String tag) {
		return queryFactory.select(Projections.constructor(PostListResponse.class , post.postId , post.title ,
						user.id , post.postDate , post.likes , post.views , freeCommit.count()))
				.from(post)
				.innerJoin(post.freeTag , freeTag).on(freeTag.tagData.eq(tag))
				.innerJoin(post.writer , user).on(post.writer.eq(user))
				.leftJoin(post.freeCommit , freeCommit).on(freeCommit.post.eq(post))
				.groupBy(post.postId , post.title , user)
				.orderBy(post.postId.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
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

	@Override
	public List<PostListResponse> getPostList(Pageable pageable) {
		return queryFactory.select(Projections.constructor(PostListResponse.class , post.postId
						, post.title , user.id , post.postDate , post.likes
						, post.views , freeCommit.count()))
				.from(post)
				.innerJoin(post.writer , user).on(post.writer.eq(user))
				.leftJoin(post.freeCommit , freeCommit).on(freeCommit.post.eq(post))
				.groupBy(post.postId , post.title , user)
				.orderBy(post.postId.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
	}

	@Override
	public long getTotalNumberOfPosts() {
		return queryFactory.select(post.count())
				.from(post)
				.fetchOne();
	}

	@Override
	public List<String> findTagsInPostId(long postId) {
		return queryFactory.select(freeTag.tagData)
				.from(freeTag)
				.leftJoin(post).on(post.freeTag.contains(freeTag))
				.where(post.postId.eq(postId))
				.fetch();
	}

	@Override
	public void updatePostView(long postId) {
		queryFactory.update(post)
				.set(post.views , post.views.add(1))
				.where(post.postId.eq(postId))
				.execute();
	}

}
