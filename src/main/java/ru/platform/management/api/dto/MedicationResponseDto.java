package ru.platform.management.api.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public record MedicationResponseDto(
        UUID id,
        UUID clinicId,
        String clinicName,
        String name,
        String description,
        String manufacturer,
        String batchNumber,
        LocalDate expiryDate,
        Integer quantityInStock,
        Integer minStockLevel,
        Integer reorderQuantity,
        BigDecimal pricePerUnit,
        Boolean isPrescriptionOnly
) {
}
