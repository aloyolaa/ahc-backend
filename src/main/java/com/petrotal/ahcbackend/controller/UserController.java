package com.petrotal.ahcbackend.controller;

import com.petrotal.ahcbackend.dto.ResponseDto;
import com.petrotal.ahcbackend.entity.User;
import com.petrotal.ahcbackend.service.security.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@RequestBody User user) {
        return new ResponseEntity<>(
                new ResponseDto(
                        userService.save(user),
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
}
