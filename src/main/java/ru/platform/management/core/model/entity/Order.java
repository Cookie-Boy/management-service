package ru.platform.management.core.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {

    public Order(Medication medication, LocalDateTime orderDate, Integer quantity, BigDecimal totalAmount, String status) {
        this.medication = medication;
        this.orderDate = orderDate;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.status = status;
        this.isPaid = false;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID")
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medication_id", nullable = false)
    private Medication medication;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime orderDate;

    @NotNull
    @Positive(message = "Количество должно быть положительным")
    @Column(nullable = false)
    private Integer quantity;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "Сумма должна быть больше 0")
    @Column(nullable = false)
    private BigDecimal totalAmount;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false)
    private String status;

    @NotNull
    @Column(nullable = false)
    private Boolean isPaid = false;

    @Size(max = 1000)
    private String notes;

    @PrePersist
    private void onCreate() {
        if (orderDate == null) {
            orderDate = LocalDateTime.now();
        }
        if (isPaid == null) {
            isPaid = false;
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", medication=" + (medication != null ? medication.getId() : null) +
                ", orderDate=" + orderDate +
                ", quantity=" + quantity +
                ", totalAmount=" + totalAmount +
                ", status='" + status + '\'' +
                ", isPaid=" + isPaid +
                ", notes='" + notes + '\'' +
                '}';
    }
}
