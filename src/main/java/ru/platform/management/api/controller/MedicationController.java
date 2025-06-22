package ru.platform.management.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.platform.management.api.dto.MedicationRequestDto;
import ru.platform.management.api.dto.MedicationResponseDto;
import ru.platform.management.api.mapper.MedicationMapper;
import ru.platform.management.core.model.entity.Medication;
import ru.platform.management.core.service.MedicationService;

@RestController
@RequiredArgsConstructor
public class MedicationController {

    // Сначала напиши тесты для медикаментов! Ну и для докторов тоже, а потом уже пиши саму реализацию. (TTD)

    private final MedicationService medicationService;

    public ResponseEntity<MedicationResponseDto> createMedication(@RequestBody MedicationRequestDto medicationRequestDto) {
        MedicationResponseDto responseDto = medicationService.createMedication(medicationRequestDto);
        return ResponseEntity.ok(responseDto);
    }

}
