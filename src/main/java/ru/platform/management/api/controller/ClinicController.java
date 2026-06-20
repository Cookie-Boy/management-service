package ru.platform.management.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.platform.management.api.dto.ClinicRequestDto;
import ru.platform.management.api.dto.ClinicResponseDto;
import ru.platform.management.api.dto.SuccessResponseDto;
import ru.platform.management.core.service.ClinicService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/management/clinics")
@RequiredArgsConstructor
public class ClinicController {

    private final ClinicService clinicService;

    @PostMapping
    public ResponseEntity<ClinicResponseDto> createClinic(@RequestBody ClinicRequestDto clinicRequestDto) {
        log.info("Try to create new clinic. Input data (ClinicRequestDto):");
        log.info(String.valueOf(clinicRequestDto));
        ClinicResponseDto createdClinic = clinicService.createClinic(clinicRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClinic);
    }

    @GetMapping
    public ResponseEntity<List<ClinicResponseDto>> getAllClinics() {
        List<ClinicResponseDto> clinicDtoList = clinicService.getAllClinics();
        return ResponseEntity.ok(clinicDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClinicResponseDto> getClinic(@PathVariable UUID id) {
        ClinicResponseDto clinic = clinicService.getClinicById(id);
        return ResponseEntity.ok(clinic);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClinicResponseDto> updateClinic(@PathVariable UUID id,
                                                          @RequestBody ClinicRequestDto clinicDto) {
        ClinicResponseDto clinic = clinicService.updateClinicById(id, clinicDto);
        return ResponseEntity.ok(clinic);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponseDto> deleteClinic(@PathVariable UUID id) {
        SuccessResponseDto response = clinicService.deleteClinicById(id);
        return ResponseEntity.ok(response);
    }
}
