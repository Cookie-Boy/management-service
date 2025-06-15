package ru.platform.management.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.platform.management.api.dto.MedicationRequestDto;
import ru.platform.management.api.dto.MedicationResponseDto;
import ru.platform.management.api.mapper.MedicationMapper;
import ru.platform.management.core.model.entity.Medication;
import ru.platform.management.core.service.MedicationService;

@RestController
@RequiredArgsConstructor
public class MedicationController {

    private final MedicationService medicationService;
    private final MedicationMapper medicationMapper;

    public ResponseEntity<MedicationResponseDto> createMedication(MedicationRequestDto requestDto) {
        Medication medication = medicationMapper.toEntity(requestDto);
        MedicationResponseDto responseDto = medicationService.createMedication(medication);
        return ResponseEntity.ok(responseDto);
    }

}
