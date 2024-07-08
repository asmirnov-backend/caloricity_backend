package ru.caloricity.main.caloricity.ingredientCatalog;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.caloricity.main.caloricity.ingredientCatalog.dto.IngredientCatalogCreateDto;
import ru.caloricity.main.caloricity.ingredientCatalog.dto.IngredientCatalogDto;
import ru.caloricity.main.caloricity.ingredientCatalog.dto.IngredientCatalogInPageDto;
import ru.caloricity.main.common.exception.EntityNotFoundException;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IngredientCatalogService {
    private final IngredientCatalogRepository repository;
    private final ModelMapper modelMapper;

    public Optional<IngredientCatalog> findById(UUID id) {
        return repository.findById(id);
    }

    public Page<IngredientCatalogInPageDto> findAll(Pageable pageable) {
        return repository.findAllProjectedBy(pageable);
    }

    public IngredientCatalogDto findDtoByIdOrThrow(UUID id) throws EntityNotFoundException {
        return repository.findDtoById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void create(IngredientCatalogCreateDto createDto) {
        IngredientCatalog entity = modelMapper.map(createDto, IngredientCatalog.class);
        entity.setId(UUID.randomUUID());
        repository.save(entity);
    }

    public void update(UUID id, IngredientCatalogCreateDto createDto){
        Optional<IngredientCatalog> currentEntity = findById(id);
        if (currentEntity.isPresent()) {
            BeanUtils.copyProperties(createDto, currentEntity, "id");
            repository.save(currentEntity.get());
        }
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
