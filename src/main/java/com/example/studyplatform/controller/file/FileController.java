package com.example.studyplatform.controller.file;

import com.example.studyplatform.dto.file.FileUploadRequest;
import com.example.studyplatform.dto.file.FileUploadResponse;
import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.service.file.S3FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(name = "file-controller", description = "파일 업로드 API")
@RequiredArgsConstructor
@RestController
public class FileController {
    private final S3FileService s3FileService;

    @Operation(
            summary = "파일 업로드",
            description = "채팅방에 파일을 업로드 합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "파일업로드 생성 성공", content = @Content(schema = @Schema(implementation = FileUploadResponse.class)))
            }
    )
    @PostMapping("/api/files/upload")
    public Response uploadFile(@ModelAttribute FileUploadRequest req) throws IOException {
        return Response.success(s3FileService.upload(req));
    }

    @Operation(
            summary = "파일 삭제",
            description = "채팅방에 파일을 전부 삭제합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "파일업로드 삭제 성공", content = @Content(schema = @Schema(implementation = Response.class)))
            },
            parameters = {
                    @Parameter(name = "roomId", description = "채팅방 ID", in = ParameterIn.PATH)
            }
    )
    @DeleteMapping("/api/files/{roomId}")
    public Response deleteFile(@PathVariable String roomId) {
        s3FileService.deletePrefixFiles(roomId);
        return Response.success();
    }
}
