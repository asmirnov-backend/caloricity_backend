package ru.caloricity.drySubstancesResearch;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.caloricity.common.exception.EntityNotFoundException;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DrySubstancesResearchService {
    private final DrySubstancesResearchRepository repository;
    private final DrySubstancesResearchMapper mapper;

    public Optional<DrySubstancesResearch> findById(UUID id) {
        return repository.findById(id);
    }

    public Page<DrySubstancesResearchInPageDto> findAll(Pageable pageable) {
        return repository.findAllProjectedBy(pageable);
    }

    public DrySubstancesResearchDto findDtoByIdOrThrow(UUID id) throws EntityNotFoundException {
        return repository.findDtoById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void create(DrySubstancesResearchCreateDto createDto) {
        DrySubstancesResearch entity = mapper.toEntity(createDto);
        repository.save(entity);
    }

    public void update(UUID id, DrySubstancesResearchCreateDto createDto){
        Optional<DrySubstancesResearch> currentEntity = findById(id);
        if (currentEntity.isPresent()) {
            BeanUtils.copyProperties(createDto, currentEntity, "id");
            repository.save(currentEntity.get());
        }
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
