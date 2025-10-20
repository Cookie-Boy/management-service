package ru.platform.management.api.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record MedicationRequestDto(
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
