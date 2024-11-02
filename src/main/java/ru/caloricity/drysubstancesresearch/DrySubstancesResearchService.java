package ru.caloricity.drysubstancesresearch;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.caloricity.common.dto.IdDto;
import ru.caloricity.common.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DrySubstancesResearchService {
    private final DrySubstancesResearchRepository repository;
    private final DrySubstancesResearchMapper mapper;

    public Optional<DrySubstancesResearch> findById(UUID id) {
        return repository.findById(id);
    }

    public Page<DrySubstancesResearchDto> findAll(Pageable pageable, UUID probeId) {
        Page<DrySubstancesResearch> entities = repository.findAllByProbeId(pageable, probeId);
        List<DrySubstancesResearchDto> dtoList = entities.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, entities.getTotalElements());

    }

    public DrySubstancesResearchDto findDtoByIdOrThrow(UUID id) {
        return repository.findById(id).map(mapper::toDto).orElseThrow(() -> new EntityNotFoundException(id, DrySubstancesResearch.class));
    }

    public IdDto create(DrySubstancesResearchCreateDto createDto) {
        DrySubstancesResearch entity = mapper.toEntity(createDto);
        repository.save(entity);
        return new IdDto(entity.getId());
    }

    public void update(UUID id, DrySubstancesResearchUpdateDto dto) {
        Optional<DrySubstancesResearch> currentEntity = findById(id);
        if (currentEntity.isPresent()) {
            BeanUtils.copyProperties(dto, currentEntity.get(), "id");
            repository.save(currentEntity.get());
        }
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
