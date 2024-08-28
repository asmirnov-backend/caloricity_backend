package ru.caloricity.drysubstancesresearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource(exported = false)
interface DrySubstancesResearchRepository extends JpaRepository<DrySubstancesResearch, UUID> {
    Page<DrySubstancesResearchDto> findAllProjectedByProbeId(Pageable pageable, UUID probeId);

    Optional<DrySubstancesResearchDto> findDtoById(UUID id);
}