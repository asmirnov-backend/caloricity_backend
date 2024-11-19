package ru.caloricity.probe.research.carbohydratesresearch;

import lombok.RequiredArgsConstructor;
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
public class CarbohydratesResearchService {
    private final CarbohydratesResearchRepository repository;
    private final CarbohydratesResearchMapper mapper;

    public Optional<CarbohydratesResearch> findById(UUID id) {
        return repository.findById(id);
    }

    public Page<CarbohydratesResearchDto> findAll(Pageable pageable, UUID probeId) {
        Page<CarbohydratesResearch> entities = repository.findAllByProbeId(pageable, probeId);
        List<CarbohydratesResearchDto> dtoList = entities.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, entities.getTotalElements());
    }

    public CarbohydratesResearchDto findDtoByIdOrThrow(UUID id) {
        return repository.findById(id).map(mapper::toDto).orElseThrow(() -> new EntityNotFoundException(id, CarbohydratesResearch.class));
    }

    public IdDto create(CarbohydratesResearchCreateDto createDto) {
        CarbohydratesResearch entity = mapper.toEntity(createDto);
        repository.save(entity);
        return new IdDto(entity.getId());
    }

    public void update(UUID id, CarbohydratesResearchUpdateDto dto) {
        CarbohydratesResearch research = findById(id).orElseThrow(() -> new EntityNotFoundException(id, CarbohydratesResearch.class));
        mapper.updateEntity(research, dto);
        repository.save(research);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
