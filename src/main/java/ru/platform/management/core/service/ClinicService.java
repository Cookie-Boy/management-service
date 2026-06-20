package ru.platform.management.core.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.platform.management.api.dto.ClinicRequestDto;
import ru.platform.management.api.dto.ClinicResponseDto;
import ru.platform.management.api.dto.SuccessResponseDto;
import ru.platform.management.api.mapper.ClinicMapper;
import ru.platform.management.core.model.entity.Clinic;
import ru.platform.management.core.repository.jpa.ClinicRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClinicService {

    private final ClinicMapper clinicMapper;
    private final ClinicRepository clinicRepository;

    @Transactional
    public ClinicResponseDto createClinic(ClinicRequestDto clinicDto) {
        
        Clinic clinic = clinicMapper.toEntity(clinicDto);
        clinic = clinicRepository.save(clinic);
        return clinicMapper.toDto(clinic);
    }

    public List<ClinicResponseDto> getAllClinics() {
        List<Clinic> clinics = clinicRepository.findAll();
        return clinicMapper.toDto(clinics);
    }

    public ClinicResponseDto getClinicById(UUID id) {
        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Клиника с таким UUID не найдена"));
        return clinicMapper.toDto(clinic);
    }

    @Transactional
    public ClinicResponseDto updateClinicById(UUID id, ClinicRequestDto clinicDto) {
        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Клиника с таким UUID не найдена"));

        clinicMapper.updateClinicFromDto(clinicDto, clinic);
        clinic = clinicRepository.save(clinic);
        return clinicMapper.toDto(clinic);
    }

    @Transactional
    public SuccessResponseDto deleteClinicById(UUID id) {
        clinicRepository.deleteById(id);
        return new SuccessResponseDto("Клиника с UUID '" + id + "' успешно удалена.");
    }

    public boolean existsById(UUID id) {
        return clinicRepository.existsById(id);
    }
}
