package ru.caloricity.probe;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
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

    public ProbeDto findDtoByIdOrThrow(UUID id) {
        return repository.findDtoById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Probe getReferenceById(UUID id) {
        return repository.getReferenceById(id);
    }

    @Transactional
    public IdDto create(ProbeCreateDto createDto) {
        Probe entity = mapper.toEntity(createDto);
        repository.save(entity);
        return new IdDto(entity.getId());
    }

    // TODO throw new ResourceNotFoundException()
    @Transactional
    public void update(UUID id, ProbeUpdateDto dto) {
        Optional<Probe> currentEntity = findById(id);
        if (currentEntity.isPresent()) {
            BeanUtils.copyProperties(dto, currentEntity.get(), "id");
            repository.save(currentEntity.get());
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Transactional
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
