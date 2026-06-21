package ru.platform.management.api.mapper;

import org.mapstruct.*;
import ru.platform.management.api.dto.MedicationRequestDto;
import ru.platform.management.api.dto.MedicationResponseDto;
import ru.platform.management.core.model.entity.Medication;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MedicationMapper {
    Medication toEntity(MedicationRequestDto dto);

    @Mapping(source = "clinic.id", target = "clinicId")
    @Mapping(source = "clinic.name", target = "clinicName")
    MedicationResponseDto toDto(Medication entity);

    List<MedicationResponseDto> toDto(List<Medication> medicines);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateMedicationFromDto(MedicationRequestDto dto, @MappingTarget Medication entity);
}
