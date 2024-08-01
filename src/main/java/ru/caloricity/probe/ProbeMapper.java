package ru.caloricity.probe;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProbeMapper {
    Probe toEntity(ProbeCreateDto dto);
}
