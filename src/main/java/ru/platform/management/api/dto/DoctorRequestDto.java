package ru.platform.management.api.dto;

import ru.platform.management.core.model.Specialization;

import java.time.LocalDate;
import java.time.LocalTime;

public record DoctorRequestDto(
        String firstName,
        String lastName,
        String middleName,
        Specialization specialization,
        String licenseNumber,
        String phoneNumber,
        String email,
        LocalDate hireDate,
        LocalTime startWorkingDay,
        LocalTime endWorkingDay,
        String bio
) {
}
