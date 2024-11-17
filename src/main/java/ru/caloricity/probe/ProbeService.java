package ru.caloricity.probe;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.caloricity.common.dto.IdDto;
import ru.caloricity.common.exception.EntityNotFoundException;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProbeService {
    private final ProbeRepository repository;
    private final ProbeMapper mapper;

    public Optional<Probe> findById(UUID id) {
        return repository.findById(id);
    }

    public Page<ProbeInPageDto> findAll(Pageable pageable, @Nullable String search) {
        if (search != null) {
            return repository.findAllByCodeLikeIgnoreCase(pageable, "%" + search + "%");
        }
        return repository.findAllDtoBy(pageable);
    }

    public ProbeDto findProbeWithResearchesAndIngredientsByIdOrThrow(UUID id) {
        return repository.findProbeWithResearchesAndIngredientsById(id).map(mapper::toDto).orElseThrow(() -> new EntityNotFoundException(id, Probe.class));
    }

    public Probe getExistingReferenceByIdOrThrow(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(id, Probe.class);
        }

        return repository.getReferenceById(id);
    }

    public IdDto create(ProbeCreateDto createDto) {
        Probe entity = mapper.toEntity(createDto);
        repository.save(entity);
        return new IdDto(entity.getId());
    }

    public void update(UUID id, ProbeUpdateDto dto) {
        Probe probe = findById(id).orElseThrow(() -> new EntityNotFoundException(id, Probe.class));
        mapper.updateEntity(probe, dto);
        repository.save(probe);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
