package ru.caloricity.ingredient;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.caloricity.common.dto.IdDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository repository;
    private final ModelMapper modelMapper;

    public Optional<Ingredient> findById(UUID id) {
        return repository.findById(id);
    }

    public Page<IngredientInPageDto> findAll(Pageable pageable, UUID probeId) {
        Page<Ingredient> ingredientEntities = repository.findAllByProbeId(pageable, probeId);
        List<IngredientInPageDto> dtoList = ingredientEntities.stream()
                .map(e -> modelMapper.map(e, IngredientInPageDto.class))
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, ingredientEntities.getTotalElements());
    }

    public IdDto create(IngredientCreateDto createDto) {
        Ingredient entity = modelMapper.map(createDto, Ingredient.class);
        entity.setId(UUID.randomUUID());
        repository.save(entity);
        return new IdDto(entity.getId());
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
