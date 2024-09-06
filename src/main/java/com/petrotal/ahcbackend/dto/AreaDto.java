package com.petrotal.ahcbackend.dto;

import com.petrotal.ahcbackend.validator.annotation.ExistsByAreaName;
import jakarta.validation.constraints.NotBlank;

public record AreaDto(
        Long id,
        @NotBlank(message = "{NotBlank.area.name}")
        @ExistsByAreaName
        String name
) {
}
