package ru.platform.management.core.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "medications")
@Getter
@Setter
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID")
    private UUID id;

    @NotBlank(message = "Название лекарства не может быть пустым")
    @Size(max = 30, message = "Название слишком длинное (макс. 30 символов)")
    private String name;

    @Size(max = 500, message = "Описание слишком длинное (макс. 500 символов)")
    private String description;

    @NotBlank(message = "Производитель не может быть пустым")
    @Size(max = 100, message = "Название производителя слишком длинное (макс. 100 символов)")
    private String manufacturer;

    @NotBlank(message = "Номер партии не может быть пустым")
    @Pattern(regexp = "^[A-Za-z0-9-]{5,20}$", message = "Некорректный формат номера партии")
    private String batchNumber;

    @Future(message = "Срок годности должен быть в будущем")
    private LocalDate expiryDate;

    @PositiveOrZero(message = "Количество на складе не может быть отрицательным")
    private Integer quantityInStock;

    @PositiveOrZero(message = "Минимальный уровень запаса не может быть отрицательным")
    private Integer minStockLevel;

    @Positive(message = "Количество для повторного заказа должно быть больше 0")
    private Integer reorderQuantity;

    @DecimalMin(value = "0.0", inclusive = false, message = "Цена за единицу должна быть больше 0")
    @Digits(integer = 10, fraction = 2, message = "Цена должна содержать не более 2 знаков после запятой")
    private BigDecimal pricePerUnit;

    @NotNull(message = "Поле 'Только по рецепту' не может быть null")
    private Boolean isPrescriptionOnly;

    @AssertTrue(message = "Минимальный уровень запаса не может превышать текущий запас")
    public Boolean isStockLevelValid() {
        return minStockLevel <= quantityInStock;
    }

    @AssertTrue(message = "Количество для заказа должно быть больше минимального уровня")
    public Boolean isReorderQuantityValid() {
        return reorderQuantity > minStockLevel;
    }

    @Override
    public String toString() {
        return "Medication{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", batchNumber='" + batchNumber + '\'' +
                ", expiryDate=" + expiryDate +
                ", quantityInStock=" + quantityInStock +
                ", minStockLevel=" + minStockLevel +
                ", reorderQuantity=" + reorderQuantity +
                ", pricePerUnit=" + pricePerUnit +
                ", isPrescriptionOnly=" + isPrescriptionOnly +
                '}';
    }
}