package ru.caloricity.probe.research.proteinsresearch;

import lombok.RequiredArgsConstructor;
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
@Transactional
public class ProteinsResearchService {
    private final ProteinsResearchRepository repository;
    private final ProteinsResearchMapper mapper;

    public Optional<ProteinsResearch> findById(UUID id) {
        return repository.findById(id);
    }

    public Page<ProteinsResearchDto> findAll(Pageable pageable, UUID probeId) {
        return repository.findAllProjectedByProbeId(pageable, probeId);
    }

    public ProteinsResearchDto findDtoByIdOrThrow(UUID id) {
        return repository.findDtoById(id).orElseThrow(() -> new EntityNotFoundException(id, ProteinsResearch.class));
    }

    public IdDto create(ProteinsResearchCreateDto createDto) {
        ProteinsResearch entity = mapper.toEntity(createDto);
        repository.save(entity);
        return new IdDto(entity.getId());
    }

    public void update(UUID id, ProteinsResearchUpdateDto dto) {
        ProteinsResearch research = findById(id).orElseThrow(() -> new EntityNotFoundException(id, ProteinsResearch.class));
        mapper.updateEntity(research, dto);
        repository.save(research);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
