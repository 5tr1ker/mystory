package com.team.mystory.post.attachment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentResponse {
    private long attachmentId;
    private String realFileName;
    private String s3Url;
    private long fileSize;
    private String uuidFileName;
}
