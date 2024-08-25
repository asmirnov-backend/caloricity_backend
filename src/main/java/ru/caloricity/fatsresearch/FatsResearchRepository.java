package ru.caloricity.fatsresearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource(exported = false)
interface FatsResearchRepository extends JpaRepository<FatsResearch, UUID> {
    Page<FatsResearchInPageDto> findAllProjectedByProbeId(Pageable pageable, UUID probeId);

    Optional<FatsResearchDto> findDtoById(UUID id);
}