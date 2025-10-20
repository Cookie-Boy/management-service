package ru.platform.management.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import ru.platform.management.core.model.Specialization;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Builder
public record DoctorResponseDto (
        UUID id,
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