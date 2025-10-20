package ru.platform.management.api.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.platform.management.api.dto.DoctorRequestDto;
import ru.platform.management.api.dto.DoctorResponseDto;
import ru.platform.management.core.model.entity.Doctor;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    Doctor toEntity(DoctorRequestDto dto);

    DoctorResponseDto toDto(Doctor doctor);
    List<DoctorResponseDto> toDto(List<Doctor> doctors);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDoctorFromDto(DoctorRequestDto dto, @MappingTarget Doctor entity);
}
