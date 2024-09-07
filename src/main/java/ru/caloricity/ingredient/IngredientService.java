package ru.caloricity.ingredient;

import jakarta.annotation.Nullable;
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
public class IngredientService {
    private final IngredientRepository repository;
    private final IngredientMapper mapper;

    public Optional<Ingredient> findById(UUID id) {
        return repository.findById(id);
    }

    public Page<IngredientInPageDto> findAll(Pageable pageable, @Nullable String search) {
        if (search != null) {
            return repository.findAllByNameLikeIgnoreCase(pageable, "%" + search + "%");
        }
        return repository.findAllDtoBy(pageable);
    }

    public IngredientDto findDtoByIdOrThrow(UUID id) {
        return repository.findDtoById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Ingredient getReferenceById(UUID id) {
        return repository.getReferenceById(id);
    }

    @Transactional
    public IdDto create(IngredientCreateDto createDto) {
        Ingredient entity = mapper.toEntity(createDto);
        entity.setId(UUID.randomUUID());
        repository.save(entity);
        return new IdDto(entity.getId());
    }

    @Transactional
    public void update(UUID id, IngredientCreateDto dto) {
        Optional<Ingredient> currentEntity = findById(id);
        if (currentEntity.isPresent()) {
            BeanUtils.copyProperties(dto, currentEntity.get(), "id");
            repository.save(currentEntity.get());
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Transactional
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
