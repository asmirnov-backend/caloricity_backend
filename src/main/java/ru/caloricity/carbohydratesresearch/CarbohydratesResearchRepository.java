package ru.caloricity.carbohydratesresearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(exported = false)
interface CarbohydratesResearchRepository extends JpaRepository<CarbohydratesResearch, UUID> {
    Page<CarbohydratesResearch> findAllByProbeId(Pageable pageable, UUID probeId);
}