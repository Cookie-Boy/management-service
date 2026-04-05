package ru.platform.management.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.platform.management.api.dto.DoctorRequestDto;
import ru.platform.management.api.dto.DoctorResponseDto;
import ru.platform.management.api.dto.SuccessResponseDto;
import ru.platform.management.core.service.DoctorService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/management/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping
    public ResponseEntity<DoctorResponseDto> createDoctor(@RequestBody DoctorRequestDto doctorRequestDto) {
        DoctorResponseDto createdDoctor = doctorService.createDoctor(doctorRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDoctor);
    }

    @GetMapping
    public ResponseEntity<List<DoctorResponseDto>> getAllDoctors() {
        List<DoctorResponseDto> doctorDtoList = doctorService.getAllDoctors();
        return ResponseEntity.ok(doctorDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponseDto> getDoctor(@PathVariable UUID id) {
        DoctorResponseDto doctor = doctorService.getDoctorById(id);
        return ResponseEntity.ok(doctor);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DoctorResponseDto> updateDoctor(@PathVariable UUID id,
                                                          @RequestBody DoctorRequestDto doctorDto) {
        DoctorResponseDto doctor = doctorService.updateDoctorById(id, doctorDto);
        return ResponseEntity.ok(doctor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponseDto> deleteDoctor(@PathVariable UUID id) {
        SuccessResponseDto response = doctorService.deleteDoctorById(id);
        return ResponseEntity.ok(response);
    }
}
