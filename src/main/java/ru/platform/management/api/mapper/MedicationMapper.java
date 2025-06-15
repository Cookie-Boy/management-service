package ru.platform.management.api.mapper;

import org.mapstruct.Mapper;
import ru.platform.management.api.dto.MedicationRequestDto;
import ru.platform.management.api.dto.MedicationResponseDto;
import ru.platform.management.core.model.entity.Medication;

@Mapper(componentModel = "spring")
public interface MedicationMapper {
    Medication toEntity(MedicationRequestDto dto);

    MedicationResponseDto toDto(Medication entity);
}
