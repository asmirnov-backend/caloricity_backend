package ru.caloricity.probe.research.proteinsresearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource(exported = false)
public interface ProteinsResearchRepository extends JpaRepository<ProteinsResearch, UUID> {
    Page<ProteinsResearchDto> findAllProjectedByProbeId(Pageable pageable, UUID probeId);

    Optional<ProteinsResearchDto> findDtoById(UUID id);
}