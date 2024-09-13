package ru.caloricity.probeingredient;

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
public class ProbeIngredientService {
    private final ProbeIngredientRepository repository;
    private final ProbeIngredientMapper mapper;

    public Optional<ProbeIngredient> findById(UUID id) {
        return repository.findById(id);
    }

    public Page<ProbeIngredientInPageDto> findAll(Pageable pageable) {
        Page<ProbeIngredient> entities = repository.findAll(pageable);
        List<ProbeIngredientInPageDto> dtoList = entities.stream()
                .map(mapper::toPageDto)
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, entities.getTotalElements());
    }

    public ProbeIngredientDto findDtoByIdOrThrow(UUID id) {
        return repository.findDtoById(id).orElseThrow(() -> new EntityNotFoundException(id, ProbeIngredient.class));
    }

    @Transactional
    public IdDto create(ProbeIngredientCreateDto createDto) {
        // TODO add error handling when referenced ingredient or probe does not exist
        ProbeIngredient entity = mapper.toEntity(createDto);
        repository.save(entity);
        return new IdDto(entity.getId());
    }

    @Transactional
    public void update(UUID id, ProbeIngredientUpdateDto dto) {
        Optional<ProbeIngredient> currentEntity = findById(id);
        if (currentEntity.isPresent()) {
            BeanUtils.copyProperties(dto, currentEntity.get(), "id");
            repository.save(currentEntity.get());
        } else {
            throw new EntityNotFoundException(id, ProbeIngredient.class);
        }
    }

    @Transactional
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
