package com.team.mystory.post.attachment.service;

import com.team.mystory.post.attachment.domain.Attachment;
import com.team.mystory.post.attachment.dto.AttachmentResponse;
import com.team.mystory.post.attachment.repository.AttachmentRepository;
import com.team.mystory.post.post.domain.Post;
import com.team.mystory.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final S3Service s3Service;

    @Transactional
    public void fileUpload(List<MultipartFile> multipartFiles , Post post) throws IOException {
        if(multipartFiles != null) {
            for (MultipartFile multipartFile : multipartFiles) {
                String uuid = createUUIDString();
                String s3Url = s3Service.uploadFileToS3(multipartFile , uuid);

                Attachment attachment = Attachment.createAttachment(uuid, s3Url, multipartFile);
                post.addFreeAttach(attachment);
            }
        }
    }

    public String createUUIDString() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    @Transactional
    public void deletedAttachment(long[] deletedFileIds , long postId) {
        List<AttachmentResponse> attachments = attachmentRepository.findAttachmentsByPostId(postId);
        List<Long> deletedAttachmentId = Arrays.stream(deletedFileIds)
                .boxed().collect(Collectors.toCollection(ArrayList::new));

        for(AttachmentResponse attachmentResponse : attachments) {
            if(deletedAttachmentId.contains(attachmentResponse.getAttachmentId())) {
                attachmentRepository.deleteById(attachmentResponse.getAttachmentId());

                s3Service.deleteFile(attachmentResponse.getUuidFileName());
            }
        }
    }

}
