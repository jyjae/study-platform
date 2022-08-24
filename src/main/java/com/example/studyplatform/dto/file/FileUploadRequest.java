package com.example.studyplatform.dto.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadRequest {
    private MultipartFile multipartFile;

    private String roomId;
}
