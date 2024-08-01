package ru.caloricity.drySubstancesResearch;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DrySubstancesResearchMapper {
    DrySubstancesResearch toEntity(DrySubstancesResearchCreateDto dto);
}
