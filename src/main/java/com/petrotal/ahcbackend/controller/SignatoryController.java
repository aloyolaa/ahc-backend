package com.petrotal.ahcbackend.controller;

import com.petrotal.ahcbackend.dto.ResponseDto;
import com.petrotal.ahcbackend.service.data.SignatoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/signatory")
@RequiredArgsConstructor
public class SignatoryController {
    private final SignatoryService signatoryService;

    @PostMapping("/save/{userId}")
    public ResponseEntity<ResponseDto> save(@PathVariable Long userId, @RequestParam MultipartFile sealFile, @RequestParam MultipartFile signatureFile) {
        return new ResponseEntity<>(
                new ResponseDto(
                        signatoryService.save(userId, sealFile, signatureFile),
                        true)
                , HttpStatus.OK);
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<ResponseDto> getByUser(@PathVariable Long userId) {
        return new ResponseEntity<>(
                new ResponseDto(
                        signatoryService.getByUserId(userId),
                        true)
                , HttpStatus.OK);
    }

    @PutMapping("/update/seal/{id}")
    public ResponseEntity<ResponseDto> updateSeal(@PathVariable Long id, @RequestParam MultipartFile sealFile) {
        return new ResponseEntity<>(
                new ResponseDto(
                        signatoryService.updateSeal(id, sealFile),
                        true)
                , HttpStatus.OK);
    }

    @PutMapping("/update/signature/{id}")
    public ResponseEntity<ResponseDto> updateSignature(@PathVariable Long id, @RequestParam MultipartFile signatureFile) {
        return new ResponseEntity<>(
                new ResponseDto(
                        signatoryService.updateSignature(id, signatureFile),
                        true)
                , HttpStatus.OK);
    }
}
