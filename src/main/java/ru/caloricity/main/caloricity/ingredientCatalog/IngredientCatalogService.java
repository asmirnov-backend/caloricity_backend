package ru.caloricity.main.caloricity.ingredientCatalog;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.caloricity.main.common.dto.IdDto;
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

    public Page<IngredientCatalogInPageDto> findAll(Pageable pageable, @Nullable String search) {
        if (search != null) {
            return repository.findAllByNameLikeIgnoreCase(pageable, "%" + search + "%");
        }
        return repository.findAllProjectedBy(pageable);
    }

    public IngredientCatalogDto findDtoByIdOrThrow(UUID id) throws EntityNotFoundException {
        return repository.findDtoById(id).orElseThrow(EntityNotFoundException::new);
    }

    public IdDto create(IngredientCatalogCreateDto createDto) {
        IngredientCatalog entity = modelMapper.map(createDto, IngredientCatalog.class);
        entity.setId(UUID.randomUUID());
        repository.save(entity);
        return new IdDto(entity.getId());
    }

    public void update(UUID id, IngredientCatalogCreateDto dto) throws EntityNotFoundException {
        Optional<IngredientCatalog> currentEntity = findById(id);
        if (currentEntity.isPresent()) {
            BeanUtils.copyProperties(dto, currentEntity.get(), "id");
            repository.save(currentEntity.get());
        } else {
            throw new EntityNotFoundException();
        }
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
