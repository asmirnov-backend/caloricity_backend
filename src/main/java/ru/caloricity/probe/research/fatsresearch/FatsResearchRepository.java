package ru.caloricity.probe.research.fatsresearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(exported = false)
public interface FatsResearchRepository extends JpaRepository<FatsResearch, UUID> {
    Page<FatsResearch> findAllByProbeId(Pageable pageable, UUID probeId);
}