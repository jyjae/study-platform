package com.example.studyplatform.dto.file;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "파일 업로드 응답 DTO")
public class FileUploadResponse {
    private String url;
}
