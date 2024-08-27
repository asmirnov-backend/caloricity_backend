package ru.caloricity.ingredientCatalog;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.caloricity.common.dto.IdDto;

import java.util.UUID;

@RestController
@RequestMapping("ingredient-catalog")
@RequiredArgsConstructor
@CrossOrigin
class IngredientCatalogController {
    private final IngredientCatalogService service;

    @GetMapping
    @PageableAsQueryParam
    public Page<IngredientCatalogInPageDto> findAll(@ParameterObject Pageable pageable, @RequestParam(value = "search", required = false) @Nullable String search) {
        return service.findAll(pageable, search);
    }

    @GetMapping("{id}")
    public IngredientCatalogDto findDtoByIdOrThrow(@PathVariable(name = "id") UUID id) {
        return service.findDtoByIdOrThrow(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IdDto create(@Valid @RequestBody IngredientCatalogCreateDto createDto) {
        return service.create(createDto);
    }

    @PutMapping("{id}")
    public void update(@PathVariable(name = "id") UUID id, @Valid @RequestBody IngredientCatalogCreateDto createDto) {
        service.update(id, createDto);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable(name = "id") UUID id) {
        service.deleteById(id);
    }
}
