package ru.caloricity.ingredient;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.caloricity.common.dto.IdDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository repository;
    private final IngredientMapper mapper;

    public Optional<Ingredient> findById(UUID id) {
        return repository.findById(id);
    }

    public Page<IngredientInPageDto> findAll(Pageable pageable, UUID probeId) {
        Page<Ingredient> ingredientEntities = repository.findAllByProbeId(pageable, probeId);
        List<IngredientInPageDto> dtoList = ingredientEntities.stream()
                .map(mapper::toPageDto)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, ingredientEntities.getTotalElements());
    }

    @Transactional
    public IdDto create(IngredientCreateDto createDto) {
        Ingredient entity = mapper.toEntity(createDto);
        entity.setId(UUID.randomUUID());
        repository.save(entity);
        return new IdDto(entity.getId());
    }

    @Transactional
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
