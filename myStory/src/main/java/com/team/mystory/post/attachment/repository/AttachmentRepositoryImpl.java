package com.team.mystory.post.attachment.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.mystory.post.attachment.dto.AttachmentResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.team.mystory.post.attachment.domain.QAttachment.attachment;
import static com.team.mystory.post.post.domain.QPost.post;

@RequiredArgsConstructor
public class AttachmentRepositoryImpl implements CustomAttachmentRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<AttachmentResponse> findAttachmentsByPostId(long postId) {
        return queryFactory.select(Projections.constructor(AttachmentResponse.class, attachment.attachmentId
                        , attachment.realFileName , attachment.s3Url , attachment.fileSize , attachment.uuidFileName))
                .from(attachment)
                .innerJoin(attachment.post , post).on(post.postId.eq(postId))
                .fetch();
    }
}
