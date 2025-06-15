package ru.platform.management.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.platform.management.api.dto.MedicationResponseDto;
import ru.platform.management.api.mapper.MedicationMapper;
import ru.platform.management.core.model.entity.Medication;
import ru.platform.management.core.repository.MedicationRepository;

@Service
@RequiredArgsConstructor
public class MedicationService {

    private final MedicationRepository medicationRepository;
    private final MedicationMapper medicationMapper;

    public MedicationResponseDto createMedication(Medication medication) {
        Medication savedMedication = medicationRepository.save(medication);
        return medicationMapper.toDto(savedMedication);
    }
}
