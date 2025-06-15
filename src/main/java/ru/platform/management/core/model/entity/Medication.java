package ru.platform.management.core.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "medications")
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String name;

    private String description;

    private String manufacturer;

    private String batchNumber;

    private LocalDate expiryDate;

    private int quantityInStock;

    private int minStockLevel;

    private int reorderQuantity;

    private BigDecimal pricePerUnit;

    private boolean isPrescriptionOnly;

    // В будущем можно добавить поставщика (supplier) и место хранения
}