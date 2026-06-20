package ru.platform.management.api.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.platform.management.api.dto.ClinicRequestDto;
import ru.platform.management.api.dto.ClinicResponseDto;
import ru.platform.management.core.model.entity.Clinic;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClinicMapper {

    Clinic toEntity(ClinicRequestDto dto);

    ClinicResponseDto toDto(Clinic clinic);
    List<ClinicResponseDto> toDto(List<Clinic> clinics);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateClinicFromDto(ClinicRequestDto dto, @MappingTarget Clinic entity);
}
