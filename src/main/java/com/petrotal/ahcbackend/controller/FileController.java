package com.petrotal.ahcbackend.controller;

import com.petrotal.ahcbackend.dto.ResponseDto;
import com.petrotal.ahcbackend.dto.UploadResponse;
import com.petrotal.ahcbackend.service.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseDto> upload(@RequestParam("file") MultipartFile file) {
        fileService.processingFile(file);

        UploadResponse uploadResponse = new UploadResponse(
                "Mensaje de Éxito",
                "Archivo procesado y datos guardados."
        );

        return new ResponseEntity<>(
                new ResponseDto(
                        uploadResponse,
                        true
                )
                , HttpStatus.OK);
    }
}
