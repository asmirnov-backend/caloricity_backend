package ru.caloricity.ingredientcatalog;

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
public class IngredientCatalogService {
    private final IngredientCatalogRepository repository;
    private final IngredientCatalogMapper mapper;

    public Optional<IngredientCatalog> findById(UUID id) {
        return repository.findById(id);
    }

    public Page<IngredientCatalogInPageDto> findAll(Pageable pageable, @Nullable String search) {
        if (search != null) {
            return repository.findAllByNameLikeIgnoreCase(pageable, "%" + search + "%");
        }
        return repository.findAllDtoBy(pageable);
    }

    public IngredientCatalogDto findDtoByIdOrThrow(UUID id) {
        return repository.findDtoById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public IdDto create(IngredientCatalogCreateDto createDto) {
        IngredientCatalog entity = mapper.toEntity(createDto);
        entity.setId(UUID.randomUUID());
        repository.save(entity);
        return new IdDto(entity.getId());
    }

    @Transactional
    public void update(UUID id, IngredientCatalogCreateDto dto) {
        Optional<IngredientCatalog> currentEntity = findById(id);
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
