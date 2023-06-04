package com.team.mystory.s3.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.team.mystory.common.ResponseMessage;
import com.team.mystory.s3.exception.S3Exception;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileUploadService {
    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadFileToS3(MultipartFile file , String uuid) throws IOException {
        if(isValidFile(file)) {
            throw new S3Exception("올바르지 않은 파일입니다.");
        }

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getInputStream().available());
        amazonS3Client.putObject(bucket , uuid ,file.getInputStream() , metadata);

        return amazonS3Client.getUrl(bucket , uuid).toString();
    }

    public boolean isValidFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

        if(!extension.equals("exe") || !extension.equals("bat")) {
            return false;
        }

        return true;
    }

    public void deleteFile(String fileName) {
        boolean isObjectExist = amazonS3Client.doesObjectExist(bucket, fileName);

        if(isObjectExist) {
            amazonS3Client.deleteObject(bucket, fileName);
        }
    }

}