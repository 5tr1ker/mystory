package com.team.mystory.post.attachment.service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.team.mystory.common.ResponseCode;
import com.team.mystory.common.ResponseMessage;
import com.team.mystory.post.attachment.domain.Attachment;
import com.team.mystory.post.attachment.dto.AttachmentResponse;
import com.team.mystory.post.attachment.repository.AttachmentRepository;
import com.team.mystory.post.post.exception.PostException;
import com.team.mystory.s3.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.team.mystory.post.post.domain.Post;
import com.team.mystory.post.post.repository.PostRepository;

import static com.team.mystory.common.ResponseCode.REQUEST_SUCCESS;

@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final PostRepository postRepository;
    private final AttachmentRepository attachmentRepository;
    private final FileUploadService fileUploadService;

    @Transactional
    public ResponseMessage fileUpload(List<MultipartFile> multipartFiles, long postId) throws IOException {
        Post post = postRepository.findPostByPostId(postId)
                .orElseThrow(() -> new PostException("해당 포스트를 찾을 수 없습니다."));

        for (MultipartFile multipartFile : multipartFiles) {
            String uuid = createUUIDString();
            String s3Url = fileUploadService.uploadFileToS3(multipartFile , uuid);

            Attachment attachment = Attachment.createAttachment(uuid, s3Url, multipartFile);
            post.addFreeAttach(attachment);
        }

        return ResponseMessage.of(REQUEST_SUCCESS);
    }

    public String createUUIDString() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    @Transactional
    public void modifyAttachment(long postId) {
        List<AttachmentResponse> attachments = attachmentRepository.findAttachmentsByPostId(postId);

    }

}
