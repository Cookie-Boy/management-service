package ru.platform.management.core.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.platform.management.api.dto.DoctorRequestDto;
import ru.platform.management.api.dto.DoctorResponseDto;
import ru.platform.management.api.dto.SuccessResponseDto;
import ru.platform.management.api.mapper.DoctorMapper;
import ru.platform.management.core.model.entity.Doctor;
import ru.platform.management.core.repository.DoctorRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorService {

    private final DoctorMapper doctorMapper;
    private final DoctorRepository doctorRepository;

    @Transactional
    public DoctorResponseDto createDoctor(DoctorRequestDto doctorDto) {
        Doctor doctor = doctorMapper.toEntity(doctorDto);
        doctor = doctorRepository.save(doctor);
        return doctorMapper.toDto(doctor);
    }

    public List<DoctorResponseDto> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        return doctorMapper.toDto(doctors);
    }

    public DoctorResponseDto getDoctorById(UUID id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Доктор с таким UUID не найден"));
        return doctorMapper.toDto(doctor);
    }

    @Transactional
    public DoctorResponseDto updateDoctorById(UUID id, DoctorRequestDto doctorDto) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Доктор с таким UUID не найден"));

        doctorMapper.updateDoctorFromDto(doctorDto, doctor);
        doctor = doctorRepository.save(doctor);
        return doctorMapper.toDto(doctor);
    }

    @Transactional
    public SuccessResponseDto deleteDoctorById(UUID id) {
        doctorRepository.deleteById(id);
        return new SuccessResponseDto("Доктор с UUID '" + id + "' успешно удален.");
    }
}
