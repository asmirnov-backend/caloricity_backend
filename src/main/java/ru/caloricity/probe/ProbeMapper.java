package ru.caloricity.probe;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

@Service
public class ProbeMapper {
    Probe toEntity(@NotNull ProbeCreateDto dto) {
        return Probe.builder()
                .name(dto.name())
                .type(dto.type())
                .code(dto.code())
                .massTheory(dto.massTheory())
                .bankaEmptyMass(dto.bankaEmptyMass())
                .bankaWithProbeMass(dto.bankaWithProbeMass())
                .build();
    }

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
                .massFact(entity.getMassFact())
                .minerals(entity.getMinerals())
                .theoreticalCaloricity(entity.getTheoreticalCaloricity())
                .build();
    }

    void updateEntity(@NotNull Probe entity, @NotNull ProbeUpdateDto dto) {
        entity.setName(dto.name());
        entity.setCode(dto.code());
        entity.setMassTheory(dto.massTheory());
        entity.setBankaEmptyMass(dto.bankaEmptyMass());
        entity.setBankaWithProbeMass(dto.bankaWithProbeMass());
    }
}
