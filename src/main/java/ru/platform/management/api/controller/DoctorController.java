package ru.platform.management.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.platform.management.api.dto.DoctorRequestDto;
import ru.platform.management.api.dto.DoctorResponseDto;
import ru.platform.management.api.mapper.DoctorMapper;
import ru.platform.management.core.model.entity.Doctor;
import ru.platform.management.core.service.DoctorService;

@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorMapper doctorMapper;
    private final DoctorService doctorService;

    public ResponseEntity<DoctorResponseDto> createDoctor(DoctorRequestDto doctorRequestDto) {
        Doctor doctor = doctorMapper.toEntity(doctorRequestDto);
        DoctorResponseDto response = doctorService.createDoctor(doctor);
        return ResponseEntity.ok(response);
    }

}
