package ru.caloricity.drysubstancesresearch;

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
@RequestMapping("dry-substances-research")
@RequiredArgsConstructor
@CrossOrigin
class DrySubstancesResearchController {
    private final DrySubstancesResearchService service;

    @GetMapping
    @PageableAsQueryParam
    public Page<DrySubstancesResearchDto> findDtoByIdOrThrow(@ParameterObject Pageable pageable, @RequestParam("probe-id") UUID probeId) {
        return service.findAll(pageable, probeId);
    }

    @GetMapping("{id}")
    public DrySubstancesResearchDto findDtoByIdOrThrow(@PathVariable(name = "id") UUID id) {
        return service.findDtoByIdOrThrow(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IdDto create(@Valid @RequestBody DrySubstancesResearchCreateDto createDto) {
        return service.create(createDto);
    }

    @PutMapping("{id}")
    public void update(@PathVariable(name = "id") UUID id, @Valid @RequestBody DrySubstancesResearchUpdateDto dto) {
        service.update(id, dto);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable(name = "id") UUID id) {
        service.deleteById(id);
    }
}
