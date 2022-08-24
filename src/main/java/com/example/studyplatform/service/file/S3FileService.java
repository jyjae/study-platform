package com.example.studyplatform.service.file;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.example.studyplatform.dto.file.FileUploadRequest;
import com.example.studyplatform.dto.file.FileUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class S3FileService {
    private final AmazonS3Client amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public FileUploadResponse upload(FileUploadRequest req) throws IOException {
        MultipartFile file = req.getMultipartFile();

        // 1. S3 객체 이름 생성
        String fileName = file.getOriginalFilename();
        // S3 디렉토리 구조 : 방번호/UUID/fileName
        String key = req.getRoomId() + "/" + UUID.randomUUID().toString() + "/" + fileName;

        // 2. 파일 메타데이터 설정
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        // inputStream()으로 byte 데이터를 전달해 로컬 스토리지에 파일을 저장하지 않고 업로드 할 수 있음!
        amazonS3.putObject(new PutObjectRequest(bucket, key, file.getInputStream(), metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead)); // PublicRead 권한으로 업로드

        return new FileUploadResponse(amazonS3.getUrl(bucket, key).toString());
    }

    // 방 삭제 -> roomId 접두사를 가진 객체 모두 삭제
    public void deletePrefixFiles(String roomId) {
        for (S3ObjectSummary file : amazonS3.listObjects(bucket, roomId).getObjectSummaries()) {
            amazonS3.deleteObject(bucket, file.getKey());
        }
    }
}
