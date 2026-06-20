package ru.platform.management.api.dto;

import lombok.Builder;

@Builder
public record ClinicRequestDto (
        String name,
        String address,
        String phone
) {
}
