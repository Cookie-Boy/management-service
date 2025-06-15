package ru.platform.management.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.platform.management.api.dto.DoctorResponseDto;
import ru.platform.management.api.mapper.DoctorMapper;
import ru.platform.management.core.model.entity.Doctor;
import ru.platform.management.core.repository.DoctorRepository;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorMapper doctorMapper;
    private final DoctorRepository doctorRepository;

    public DoctorResponseDto createDoctor(Doctor doctor) {
        Doctor savedDoctor = doctorRepository.save(doctor);
        return doctorMapper.toDto(savedDoctor);
    }
}
