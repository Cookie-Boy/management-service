package ru.platform.management.api.mapper;

import org.mapstruct.Mapper;
import ru.platform.management.api.dto.DoctorRequestDto;
import ru.platform.management.api.dto.DoctorResponseDto;
import ru.platform.management.core.model.entity.Doctor;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    Doctor toEntity(DoctorRequestDto dto);

    DoctorResponseDto toDto(Doctor doctor);
    List<DoctorResponseDto> toDto(List<Doctor> doctors);
}
