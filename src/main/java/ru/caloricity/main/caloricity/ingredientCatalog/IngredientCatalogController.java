package ru.caloricity.main.caloricity.ingredientCatalog;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.caloricity.main.common.exception.EntityNotFoundException;

import java.util.UUID;

@RestController
@RequestMapping("caloricity/ingredient-catalog")
@RequiredArgsConstructor
class IngredientCatalogController {
    private final IngredientCatalogService service;

    @GetMapping
    @PageableAsQueryParam
    // TODO add search by name
    public Page<IngredientCatalogInPageDto> findDtoByIdOrThrow(@ParameterObject Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping("{id}")
    public IngredientCatalogDto findDtoByIdOrThrow(@PathVariable(name = "id") UUID id) throws EntityNotFoundException {
        return service.findDtoByIdOrThrow(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public void create(@Valid @RequestBody IngredientCatalogCreateDto createDto) {
        service.create(createDto);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@PathVariable(name = "id") UUID id, @Valid @RequestBody IngredientCatalogCreateDto createDto) {
        service.update(id, createDto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void delete(@PathVariable(name = "id") UUID id) {
        service.deleteById(id);
    }
}
