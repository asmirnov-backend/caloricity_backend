package ru.caloricity.drySubstancesResearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource(exported = false)
interface DrySubstancesResearchRepository extends JpaRepository<DrySubstancesResearch, UUID> {
    Page<DrySubstancesResearchInPageDto> findAllProjectedBy(Pageable pageable);

    Optional<DrySubstancesResearchDto> findDtoById(UUID id);
}