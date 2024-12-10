package ru.caloricity.probe;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.caloricity.probe.research.drysubstancesresearch.DrySubstancesResearchCreateDto;
import ru.caloricity.probe.research.drysubstancesresearch.DrySubstancesResearchMapper;
import ru.caloricity.probe.research.fatsresearch.FatsResearchCreateDto;
import ru.caloricity.probe.research.fatsresearch.FatsResearchMapper;
import ru.caloricity.probe.research.proteinsresearch.ProteinsResearchCreateDto;
import ru.caloricity.probe.research.proteinsresearch.ProteinsResearchMapper;

@Service
@RequiredArgsConstructor
public class ProbeMapper {
    private final DrySubstancesResearchMapper drySubstancesResearchMapper;
    private final ProteinsResearchMapper proteinsResearchMapper;
    private final FatsResearchMapper fatsResearchMapper;

    Probe toEntity(@NotNull ProbeCreateDto dto) {
        return Probe.builder()
                .name(dto.name())
                .type(dto.type())
                .code(dto.code())
                .massTheory(dto.massTheory())
                .bankaEmptyMass(dto.bankaEmptyMass())
                .bankaWithProbeMass(dto.bankaWithProbeMass())
                .fatsResearch(fatsResearchMapper.toEntity(dto.fatsResearch()))
                .proteinsResearch(proteinsResearchMapper.toEntity(dto.proteinsResearchCreateDto()))
                .drySubstancesResearch(drySubstancesResearchMapper.toEntity(dto.drySubstancesResearch()))
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
                .fatsResearch(fatsResearchMapper.toDto(entity.getFatsResearch()))
                .proteinsResearch(proteinsResearchMapper.toDto(entity.getProteinsResearch()))
                .drySubstancesResearch(drySubstancesResearchMapper.toDto(entity.getDrySubstancesResearch()))
                .build();
    }

    void updateEntity(@NotNull Probe entity, @NotNull ProbeUpdateDto dto) {
        entity.setName(dto.name());
        entity.setCode(dto.code());
        entity.setMassTheory(dto.massTheory());
        entity.setBankaEmptyMass(dto.bankaEmptyMass());
        entity.setBankaWithProbeMass(dto.bankaWithProbeMass());

        updateFatsResearch(entity, dto.fatsResearch());
        updateProteinsResearch(entity, dto.proteinsResearch());
        updateDrySubstancesResearch(entity, dto.drySubstancesResearch());
    }

    private void updateFatsResearch(@NotNull Probe entity, FatsResearchCreateDto fatsResearchDto) {
        if (fatsResearchDto != null) {
            if (entity.getFatsResearch() != null) {
                fatsResearchMapper.updateEntity(entity.getFatsResearch(), fatsResearchDto);
            } else {
                entity.setFatsResearch(fatsResearchMapper.toEntity(fatsResearchDto));
            }
        } else {
            entity.setFatsResearch(null);
        }
    }

    private void updateProteinsResearch(@NotNull Probe entity, ProteinsResearchCreateDto proteinsResearchDto) {
        if (proteinsResearchDto != null) {
            if (entity.getProteinsResearch() != null) {
                proteinsResearchMapper.updateEntity(entity.getProteinsResearch(), proteinsResearchDto);
            } else {
                entity.setProteinsResearch(proteinsResearchMapper.toEntity(proteinsResearchDto));
            }
        } else {
            entity.setProteinsResearch(null);
        }
    }

    private void updateDrySubstancesResearch(@NotNull Probe entity, DrySubstancesResearchCreateDto drySubstancesResearchDto) {
        if (drySubstancesResearchDto != null) {
            if (entity.getDrySubstancesResearch() != null) {
                drySubstancesResearchMapper.updateEntity(entity.getDrySubstancesResearch(), drySubstancesResearchDto);
            } else {
                entity.setDrySubstancesResearch(drySubstancesResearchMapper.toEntity(drySubstancesResearchDto));
            }
        } else {
            entity.setDrySubstancesResearch(null);
        }
    }
}
