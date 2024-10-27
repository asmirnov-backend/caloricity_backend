package ru.caloricity.proteinsresearch;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.caloricity.probe.ProbeMapperUtils;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ProbeMapperUtils.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ProteinsResearchMapper {
    @Mapping(source = "probeId", target = "probe", qualifiedByName = {"ProbeMapperUtils", "getExistingReferenceByIdOrThrow"})
    ProteinsResearch toEntity(ProteinsResearchCreateDto dto);
}
