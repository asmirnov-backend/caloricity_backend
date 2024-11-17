package ru.caloricity.probe;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource(exported = false)
public interface ProbeRepository extends JpaRepository<Probe, UUID> {
    Page<ProbeInPageDto> findAllDtoBy(Pageable pageable);

    Page<ProbeInPageDto> findAllByCodeLikeIgnoreCase(Pageable pageable, String like);

    @EntityGraph(value = "Probe.withResearchesAndIngredients", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Probe> findProbeWithResearchesAndIngredientsById(UUID id);
}