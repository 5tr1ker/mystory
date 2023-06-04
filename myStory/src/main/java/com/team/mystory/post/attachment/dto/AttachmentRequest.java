package com.team.mystory.post.attachment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentRequest {
    private String realFileName;
    private String uuidFileName;
    private long fileSize;
}
