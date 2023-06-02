package com.team.mystory.post.attachment.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AttachmentResponse {
    private long attachmentId;
    private String fileName;
    private String mockFileName;
    private long fileSize;
}
