package ru.caloricity.drysubstancesresearch;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.caloricity.common.dto.IdDto;
import ru.caloricity.common.exception.EntityNotFoundException;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DrySubstancesResearchService {
    private final DrySubstancesResearchRepository repository;
    private final DrySubstancesResearchMapper mapper;

    public Optional<DrySubstancesResearch> findById(UUID id) {
        return repository.findById(id);
    }

    public Page<DrySubstancesResearchDto> findAll(Pageable pageable, UUID probeId) {
        return repository.findAllProjectedByProbeId(pageable, probeId);
    }

    public DrySubstancesResearchDto findDtoByIdOrThrow(UUID id) {
        return repository.findDtoById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public IdDto create(DrySubstancesResearchCreateDto createDto) {
        DrySubstancesResearch entity = mapper.toEntity(createDto);
        repository.save(entity);
        return new IdDto(entity.getId());
    }

    @Transactional
    public void update(UUID id, DrySubstancesResearchUpdateDto dto) {
        Optional<DrySubstancesResearch> currentEntity = findById(id);
        if (currentEntity.isPresent()) {
            BeanUtils.copyProperties(dto, currentEntity.get(), "id");
            repository.save(currentEntity.get());
        }
    }

    @Transactional
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
