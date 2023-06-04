package com.team.mystory.post.attachment.controller;

import com.team.mystory.common.ResponseMessage;
import com.team.mystory.post.attachment.dto.AttachmentRequest;
import com.team.mystory.post.attachment.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService attachmentService;

    @PostMapping(value = "/upload/{postId}")
    public ResponseEntity<ResponseMessage> uploadAttachment(@RequestPart List<MultipartFile> file , @PathVariable Long postId) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(attachmentService.fileUpload(file , postId));
    }

    @DeleteMapping(value = "/upload/{postId}")
    public ResponseEntity<ResponseMessage> deleteAttachment(@RequestBody AttachmentRequest attachmentRequest, @PathVariable Long postId) {
        return ResponseEntity.status(HttpStatus.OK).body(attachmentService.deletedAttachment(attachmentRequest , postId));
    }

}
