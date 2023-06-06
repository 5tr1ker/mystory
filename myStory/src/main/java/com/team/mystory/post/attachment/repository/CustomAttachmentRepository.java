package com.team.mystory.post.attachment.repository;

import com.team.mystory.post.attachment.domain.Attachment;
import com.team.mystory.post.attachment.dto.AttachmentResponse;

import java.util.List;

public interface CustomAttachmentRepository {
    List<AttachmentResponse> findAttachmentsByPostId(long postId);

}
