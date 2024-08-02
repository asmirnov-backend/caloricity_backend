package ru.caloricity.drySubstancesResearch;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DrySubstancesResearchMapper {
    @Mapping(target="id", expression="java(java.util.UUID.randomUUID())")
    DrySubstancesResearch toEntity(DrySubstancesResearchCreateDto dto);
}
