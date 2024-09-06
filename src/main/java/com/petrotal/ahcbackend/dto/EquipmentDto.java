package com.petrotal.ahcbackend.dto;

import com.petrotal.ahcbackend.validator.annotation.ExistsByEquipmentName;
import jakarta.validation.constraints.NotBlank;

public record EquipmentDto(
        Long id,
        @NotBlank(message = "{NotBlank.equipment.name}")
        @ExistsByEquipmentName
        String name,
        @NotBlank(message = "{NotBlank.equipment.type}")
        String type
) {
}
