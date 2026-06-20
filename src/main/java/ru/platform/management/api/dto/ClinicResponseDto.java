package ru.platform.management.api.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ClinicResponseDto (
        UUID id,
        String name,
        String address,
        String phone
) {
}