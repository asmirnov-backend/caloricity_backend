package ru.caloricity.ingredient;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.caloricity.common.dto.IdDto;
import ru.caloricity.common.exception.CascadeDeleteRestrictException;
import ru.caloricity.common.exception.EntityNotFoundException;
import ru.caloricity.probeingredient.ProbeIngredient;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class IngredientService {
    private final IngredientRepository repository;
    private final IngredientMapper mapper;

    public Optional<Ingredient> findById(UUID id) {
        return repository.findById(id);
    }

    public Page<IngredientInPageDto> findAll(Pageable pageable, @Nullable String search) {
        if (search != null) {
            return repository.findAllByNameLikeIgnoreCaseOrderByName(pageable, "%" + search + "%");
        }
        return repository.findAllDtoByOrderByName(pageable);
    }

    public IngredientDto findDtoByIdOrThrow(UUID id) {
        return repository.findDtoById(id).orElseThrow(() -> new EntityNotFoundException(id, Ingredient.class));
    }

    public Ingredient getExistingReferenceByIdOrThrow(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(id, Ingredient.class);
        }

        return repository.getReferenceById(id);
    }

    public IdDto create(IngredientCreateDto createDto) {
        Ingredient entity = mapper.toEntity(createDto);
        repository.save(entity);
        return new IdDto(entity.getId());
    }

    public void update(UUID id, IngredientUpdateDto dto) {
        Ingredient ingredient = findById(id).orElseThrow(() -> new EntityNotFoundException(id, Ingredient.class));
        mapper.updateEntity(ingredient, dto);
        repository.save(ingredient);
    }

    public void deleteById(UUID id) {
        if (repository.existsByIdAndProbeIngredientsIsNotEmpty(id)) {
            throw new CascadeDeleteRestrictException(id, Ingredient.class, ProbeIngredient.class);
        }

        repository.deleteById(id);
    }
}
