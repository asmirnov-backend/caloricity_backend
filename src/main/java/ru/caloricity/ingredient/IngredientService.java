package ru.caloricity.ingredient;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.caloricity.common.dto.IdDto;
import ru.caloricity.common.exception.EntityNotFoundException;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository repository;
    private final ModelMapper modelMapper;

    public Optional<Ingredient> findById(UUID id) {
        return repository.findById(id);
    }

    public Page<IngredientInPageDto> findAll(Pageable pageable) {
        return repository.findAllDtoBy(pageable);
    }

    public IdDto create(IngredientCreateDto createDto) {
        Ingredient entity = modelMapper.map(createDto, Ingredient.class);
        entity.setId(UUID.randomUUID());
        repository.save(entity);
        return new IdDto(entity.getId());
    }

    public void update(UUID id, IngredientCreateDto dto) throws EntityNotFoundException {
        Optional<Ingredient> currentEntity = findById(id);
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
