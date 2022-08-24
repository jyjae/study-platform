package com.example.studyplatform.controller.file;

import com.example.studyplatform.dto.file.FileUploadRequest;
import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.service.file.S3FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class FileController {
    private final S3FileService s3FileService;

    @PostMapping("/api/files/upload")
    public Response uploadFile(@ModelAttribute FileUploadRequest req) throws IOException {
        return Response.success(s3FileService.upload(req));
    }

    @DeleteMapping("/api/files/{roomId}")
    public Response deleteFile(@PathVariable String roomId) {
        s3FileService.deletePrefixFiles(roomId);
        return Response.success();
    }
}
