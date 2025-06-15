package ru.platform.management.core.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @NotBlank(message = "Имя не может быть пустым")
    @Size(max = 100, message = "Имя слишком длинное, ограничение 100 символов")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Фамилия не может быть пустой")
    @Size(max = 100, message = "Фамилия слишком длинная, ограничение 100 символов")
    @Column(nullable = false)
    private String lastName;

    private String middleName;

    @Enumerated(EnumType.STRING)
    private String specialization;

    @Column(nullable = false)
    private String licenseNumber;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String email;

    @Column()
    private LocalDate hireDate;

    @Column(nullable = false)
    private LocalTime startWorkingDay;

    @Column(nullable = false)
    private LocalTime endWorkingDay;

    private String bio;
}
