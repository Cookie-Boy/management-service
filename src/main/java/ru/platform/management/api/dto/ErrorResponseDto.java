package ru.platform.management.api.dto;

public record ErrorResponseDto(
        int code,
        String message
) {
}
