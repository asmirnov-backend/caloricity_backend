package ru.caloricity.carbohydratesresearch;

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
public class CarbohydratesResearchService {
    private final CarbohydratesResearchRepository repository;
    private final CarbohydratesResearchMapper mapper;

    public Optional<CarbohydratesResearch> findById(UUID id) {
        return repository.findById(id);
    }

    public Page<CarbohydratesResearchDto> findAll(Pageable pageable, UUID probeId) {
        return repository.findAllProjectedByProbeId(pageable, probeId);
    }

    public CarbohydratesResearchDto findDtoByIdOrThrow(UUID id) {
        return repository.findDtoById(id).orElseThrow(() -> new EntityNotFoundException(id, CarbohydratesResearch.class));
    }

    @Transactional
    public IdDto create(CarbohydratesResearchCreateDto createDto) {
        CarbohydratesResearch entity = mapper.toEntity(createDto);
        repository.save(entity);
        return new IdDto(entity.getId());
    }

    @Transactional
    public void update(UUID id, CarbohydratesResearchUpdateDto dto) {
        Optional<CarbohydratesResearch> currentEntity = findById(id);
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
