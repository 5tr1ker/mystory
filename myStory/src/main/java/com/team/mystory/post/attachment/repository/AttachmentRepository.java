package com.team.mystory.post.attachment.repository;

import com.team.mystory.post.attachment.domain.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> , CustomAttachmentRepository {

}
