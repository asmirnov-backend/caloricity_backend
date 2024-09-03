package ru.caloricity.ingredient;

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
@RequestMapping("ingredients")
@RequiredArgsConstructor
@CrossOrigin
class IngredientController {
    private final IngredientService service;

    @GetMapping
    @PageableAsQueryParam
    public Page<IngredientInPageDto> findAll(@ParameterObject Pageable pageable, @RequestParam("probe-id") UUID probeId) {
        return service.findAll(pageable, probeId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IdDto create(@Valid @RequestBody IngredientCreateDto createDto) {
        return service.create(createDto);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable(name = "id") UUID id) {
        service.deleteById(id);
    }
}
