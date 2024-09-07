package ru.caloricity.probeingredient;

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
@RequestMapping("probe-ingredient")
@RequiredArgsConstructor
@CrossOrigin
class ProbeIngredientController {
    private final ProbeIngredientService service;

    @GetMapping
    @PageableAsQueryParam
    public Page<ProbeIngredientInPageDto> findAll(@ParameterObject Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping("{id}")
    public ProbeIngredientDto findDtoByIdOrThrow(@PathVariable(name = "id") UUID id) {
        return service.findDtoByIdOrThrow(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IdDto create(@Valid @RequestBody ProbeIngredientCreateDto createDto) {
        return service.create(createDto);
    }

    @PutMapping("{id}")
    public void update(@PathVariable(name = "id") UUID id, @Valid @RequestBody ProbeIngredientUpdateDto createDto) {
        service.update(id, createDto);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable(name = "id") UUID id) {
        service.deleteById(id);
    }
}
