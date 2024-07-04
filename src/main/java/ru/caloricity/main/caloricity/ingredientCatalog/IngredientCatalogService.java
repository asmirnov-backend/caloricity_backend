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

    public void create(IngredientCatalogCreateDto ingredientCatalogCreateDto) {
        IngredientCatalog ingredientCatalog = modelMapper.map(ingredientCatalogCreateDto, IngredientCatalog.class);
        ingredientCatalog.setId(UUID.randomUUID());
        repository.save(ingredientCatalog);
    }

    public void update(UUID id, IngredientCatalogCreateDto ingredientCatalogCreateDto){
        Optional<IngredientCatalog> currentIngredientCatalog = findById(id);
        if (currentIngredientCatalog.isPresent()) {
            BeanUtils.copyProperties(ingredientCatalogCreateDto, currentIngredientCatalog, "id");
            repository.save(currentIngredientCatalog.get());
        }
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
