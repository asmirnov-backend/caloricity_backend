package ru.caloricity.drysubstancesresearch;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.caloricity.probe.ProbeMapperUtils;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ProbeMapperUtils.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DrySubstancesResearchMapper {
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(source = "probeId", target = "probe", qualifiedByName = {"ProbeMapperUtils", "mapProbeIdToEntity"})
    DrySubstancesResearch toEntity(DrySubstancesResearchCreateDto dto);
}
