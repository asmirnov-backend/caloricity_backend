package ru.caloricity.fatsresearch;

import org.mapstruct.*;
import ru.caloricity.probe.ProbeMapperUtils;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ProbeMapperUtils.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface FatsResearchMapper {
    @Mapping(source = "probeId", target = "probe", qualifiedByName = {"ProbeMapperUtils", "getExistingReferenceByIdOrThrow"})
    FatsResearch toEntity(FatsResearchCreateDto dto);

    @Mapping(source = ".", target = "dryResidueWeightParallelFirst", qualifiedByName = "calcDryResidueWeightParallelFirst")
    @Mapping(source = ".", target = "dryResidueWeightParallelSecond", qualifiedByName = "calcDryResidueWeightParallelSecond")
    @Mapping(source = ".", target = "dryResidueWeightAverage", qualifiedByName = "calcDryResidueWeightAverage")
    FatsResearchDto toDto(FatsResearch entity);

    @Named("calcDryResidueWeightParallelFirst")
    default Float calcDryResidueWeightParallelFirst(FatsResearch research) {
        return research.calcDryResidueWeightParallelFirst();
    }

    @Named("calcDryResidueWeightParallelSecond")
    default Float calcDryResidueWeightParallelSecond(FatsResearch research) {
        return research.calcDryResidueWeightParallelSecond();
    }

    @Named("calcDryResidueWeightAverage")
    default Float calcDryResidueWeightAverage(FatsResearch research) {
        return research.calcDryResidueWeightAverage();
    }
}
