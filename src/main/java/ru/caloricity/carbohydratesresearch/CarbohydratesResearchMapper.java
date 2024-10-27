package ru.caloricity.carbohydratesresearch;

import org.mapstruct.*;
import ru.caloricity.probe.ProbeMapperUtils;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ProbeMapperUtils.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CarbohydratesResearchMapper {
    @Mapping(source = "probeId", target = "probe", qualifiedByName = {"ProbeMapperUtils", "getExistingReferenceByIdOrThrow"})
    CarbohydratesResearch toEntity(CarbohydratesResearchCreateDto dto);

    @Mapping(source = ".", target = "dryResidueWeightParallelFirst", qualifiedByName = "calcDryResidueWeightParallelFirst")
    @Mapping(source = ".", target = "dryResidueWeightParallelSecond", qualifiedByName = "calcDryResidueWeightParallelSecond")
    @Mapping(source = ".", target = "dryResidueWeightAverage", qualifiedByName = "calcDryResidueWeightAverage")
    CarbohydratesResearchDto toDto(CarbohydratesResearch entity);

    @Named("calcDryResidueWeightParallelFirst")
    default Float calcDryResidueWeightParallelFirst(CarbohydratesResearch research) {
        return research.calcDryResidueWeightParallelFirst();
    }

    @Named("calcDryResidueWeightParallelSecond")
    default Float calcDryResidueWeightParallelSecond(CarbohydratesResearch research) {
        return research.calcDryResidueWeightParallelSecond();
    }


    @Named("calcDryResidueWeightAverage")
    default Float calcDryResidueWeightAverage(CarbohydratesResearch research) {
        return research.calcDryResidueWeightAverage();
    }
}
