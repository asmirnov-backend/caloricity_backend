package ru.caloricity.probe.research.drysubstancesresearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(exported = false)
public interface DrySubstancesResearchRepository extends JpaRepository<DrySubstancesResearch, UUID> {
    Page<DrySubstancesResearch> findAllByProbeId(Pageable pageable, UUID probeId);
}