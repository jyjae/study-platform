package com.example.studyplatform.dto.file;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "파일 업로드 DTO")
public class FileUploadRequest {
    @NotBlank(message = "파일을 첨부해주세요")
    private MultipartFile multipartFile;

    private String roomId;
}
