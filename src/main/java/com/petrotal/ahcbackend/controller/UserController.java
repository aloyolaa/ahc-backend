package com.petrotal.ahcbackend.controller;

import com.petrotal.ahcbackend.dto.ResponseDto;
import com.petrotal.ahcbackend.dto.UserRegisterDto;
import com.petrotal.ahcbackend.service.security.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDto> register(@RequestBody UserRegisterDto userRegisterDto) {
        userService.save(userRegisterDto);
        return new ResponseEntity<>(
                new ResponseDto(
                        "Datos guardados correctamente.",
                        true)
                , HttpStatus.OK);
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<ResponseDto> getByUsername(@PathVariable String username) {
        return new ResponseEntity<>(
                new ResponseDto(
                        userService.findByUsername(username),
                        true)
                , HttpStatus.OK);
    }

    @GetMapping("/signatories")
    @PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<ResponseDto> getSignatories() {
        return new ResponseEntity<>(
                new ResponseDto(
                        List.of(
                                userService.findByRole("FIELD_MANAGER"),
                                userService.findByRole("LOGISTICS_COORDINATOR"),
                                userService.findByRole("PRODUCTION_SUPERINTENDENT"),
                                userService.findByRole("STORE")
                        ),
                        true)
                , HttpStatus.OK);
    }
}
