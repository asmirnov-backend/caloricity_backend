package ru.caloricity.carbohydratesresearch;

import org.mapstruct.*;
import ru.caloricity.probe.ProbeMapperUtils;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ProbeMapperUtils.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CarbohydratesResearchMapper {
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(source = "probeId", target = "probe", qualifiedByName = {"ProbeMapperUtils", "getExistingReferenceByIdOrThrow"})
    CarbohydratesResearch toEntity(CarbohydratesResearchCreateDto dto);

    @Mapping(source = ".", target = "dryResidueWeightParallelFirst", qualifiedByName = "calcDryResidueWeightParallelFirst")
    @Mapping(source = ".", target = "dryResidueWeightParallelSecond", qualifiedByName = "calcDryResidueWeightParallelSecond")
    @Mapping(source = ".", target = "dryResidueWeightAverage", qualifiedByName = "calcDryResidueWeightAverage")
    CarbohydratesResearchDto toDto(CarbohydratesResearch entity);

    @Named("calcDryResidueWeightParallelFirst")
    default Float calcDryResidueWeightParallelFirst(CarbohydratesResearch research) {
        return research.getByuksaParallelFirst() - research.getByuksaAfterDryingParallelFirst();
    }

    @Named("calcDryResidueWeightParallelSecond")
    default Float calcDryResidueWeightParallelSecond(CarbohydratesResearch research) {
        return research.getByuksaParallelSecond() - research.getByuksaAfterDryingParallelSecond();
    }


    @Named("calcDryResidueWeightAverage")
    default Float calcDryResidueWeightAverage(CarbohydratesResearch research) {
        return (calcDryResidueWeightParallelFirst(research) + calcDryResidueWeightParallelSecond(research)) / 2.0f;
    }
}
