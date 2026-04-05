package ru.platform.management.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.platform.management.api.dto.MedicationRequestDto;
import ru.platform.management.api.dto.MedicationResponseDto;
import ru.platform.management.api.dto.SuccessResponseDto;
import ru.platform.management.core.service.MedicationService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/management/medicines")
@RequiredArgsConstructor
public class MedicationController {

    private final MedicationService medicationService;

    @PostMapping
    public ResponseEntity<MedicationResponseDto> createMedication(@RequestBody MedicationRequestDto medicationRequestDto) {
        MedicationResponseDto responseDto = medicationService.createMedication(medicationRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<MedicationResponseDto>> getAllMedicines() {
        List<MedicationResponseDto> medicines = medicationService.getAllMedicines();
        return ResponseEntity.ok(medicines);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicationResponseDto> getMedication(@PathVariable UUID id) {
        MedicationResponseDto medication = medicationService.getMedicationById(id);
        return ResponseEntity.ok(medication);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MedicationResponseDto> updateMedication(@PathVariable UUID id,
                                                                 @RequestBody MedicationRequestDto requestDto) {
        MedicationResponseDto medication = medicationService.updateMedicationById(id, requestDto);
        return ResponseEntity.ok(medication);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponseDto> deleteMedication(@PathVariable UUID id) {
        SuccessResponseDto response = medicationService.deleteMedicationById(id);
        return ResponseEntity.ok(response);
    }
}
