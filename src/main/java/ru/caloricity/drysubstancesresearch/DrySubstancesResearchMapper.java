package ru.caloricity.drysubstancesresearch;

import org.mapstruct.*;
import ru.caloricity.probe.ProbeMapperUtils;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ProbeMapperUtils.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DrySubstancesResearchMapper {
    @Mapping(source = "probeId", target = "probe", qualifiedByName = {"ProbeMapperUtils", "getExistingReferenceByIdOrThrow"})
    DrySubstancesResearch toEntity(DrySubstancesResearchCreateDto dto);

    @Mapping(source = ".", target = "dryResidueWeightParallelFirst", qualifiedByName = "calcDryResidueWeightParallelFirst")
    @Mapping(source = ".", target = "dryResidueWeightParallelSecond", qualifiedByName = "calcDryResidueWeightParallelSecond")
    @Mapping(source = ".", target = "dryResidueWeightAverage", qualifiedByName = "calcDryResidueWeightAverage")
    DrySubstancesResearchDto toDto(DrySubstancesResearch entity);

    @Named("calcDryResidueWeightParallelFirst")
    default Float calcDryResidueWeightParallelFirst(DrySubstancesResearch research) {
        return research.calcDryResidueWeightParallelFirst();
    }

    @Named("calcDryResidueWeightParallelSecond")
    default Float calcDryResidueWeightParallelSecond(DrySubstancesResearch research) {
        return research.calcDryResidueWeightParallelSecond();
    }


    @Named("calcDryResidueWeightAverage")
    default Float calcDryResidueWeightAverage(DrySubstancesResearch research) {
        return research.calcDryResidueWeightAverage();
    }

}
