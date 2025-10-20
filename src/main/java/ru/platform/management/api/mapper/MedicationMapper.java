package ru.platform.management.api.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.platform.management.api.dto.MedicationRequestDto;
import ru.platform.management.api.dto.MedicationResponseDto;
import ru.platform.management.core.model.entity.Medication;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MedicationMapper {
    Medication toEntity(MedicationRequestDto dto);

    MedicationResponseDto toDto(Medication entity);
    List<MedicationResponseDto> toDto(List<Medication> medicines);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateMedicationFromDto(MedicationRequestDto dto, @MappingTarget Medication entity);
}
