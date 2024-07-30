package ru.caloricity.main.probe;

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
public class ProbeService {
    private final ProbeRepository repository;
    private final ModelMapper modelMapper;

    public Optional<Probe> findById(UUID id) {
        return repository.findById(id);
    }

    public Page<ProbeInPageDto> findAll(Pageable pageable, @Nullable String search) {
        if (search != null) {
            return repository.findAllByNameLikeIgnoreCase(pageable, "%" + search + "%");
        }
        return repository.findAllDtoBy(pageable);
    }

    public ProbeDto findDtoByIdOrThrow(UUID id) throws EntityNotFoundException {
        return repository.findDtoById(id).orElseThrow(EntityNotFoundException::new);
    }

    public IdDto create(ProbeCreateDto createDto) {
        Probe entity = modelMapper.map(createDto, Probe.class);
        entity.setId(UUID.randomUUID());
        repository.save(entity);
        return new IdDto(entity.getId());
    }

    public void update(UUID id, ProbeCreateDto dto) throws EntityNotFoundException {
        Optional<Probe> currentEntity = findById(id);
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
