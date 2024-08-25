package ru.caloricity.fatsResearch;

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
public class FatsResearchService {
    private final FatsResearchRepository repository;
    private final FatsResearchMapper mapper;

    public Optional<FatsResearch> findById(UUID id) {
        return repository.findById(id);
    }

    public Page<FatsResearchInPageDto> findAll(Pageable pageable, UUID probeId) {
        return repository.findAllProjectedByProbeId(pageable, probeId);
    }

    public FatsResearchDto findDtoByIdOrThrow(UUID id) {
        return repository.findDtoById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public IdDto create(FatsResearchCreateDto createDto) {
        FatsResearch entity = mapper.toEntity(createDto);
        repository.save(entity);
        return new IdDto(entity.getId());
    }

    @Transactional
    public void update(UUID id, FatsResearchUpdateDto dto) {
        Optional<FatsResearch> currentEntity = findById(id);
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
