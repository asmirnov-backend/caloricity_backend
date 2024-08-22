package ru.caloricity.probe;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProbeMapper {
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    Probe toEntity(ProbeCreateDto dto);
}
