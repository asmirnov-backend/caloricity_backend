package ru.caloricity.probe;

import jakarta.validation.constraints.NotNull;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class ProbeMapper {
    abstract Probe toEntity(ProbeCreateDto dto);

    ProbeDto toDto(@NotNull Probe entity) {
        return ProbeDto.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .name(entity.getName())
                .type(entity.getType())
                .code(entity.getCode())
                .massTheory(entity.getMassTheory())
                .bankaEmptyMass(entity.getBankaEmptyMass())
                .bankaWithProbeMass(entity.getBankaWithProbeMass())
                .massFact(entity.massFact())
                .minerals(entity.minerals())
                .build();
    }
}
