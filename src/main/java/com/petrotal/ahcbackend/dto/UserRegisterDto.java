package com.petrotal.ahcbackend.dto;

public record UserRegisterDto(
        //TODO validar que el username no se repita
        String username,
        String password,
        String firstName,
        String lastName,
        //TODO validar que el email no se repita
        String email,
        Integer role
) {
}
