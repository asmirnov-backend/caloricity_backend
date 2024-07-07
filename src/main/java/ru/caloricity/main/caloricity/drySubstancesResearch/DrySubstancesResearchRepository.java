package ru.caloricity.main.caloricity.drySubstancesResearch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(exported = false)
public interface DrySubstancesResearchRepository extends JpaRepository<DrySubstancesResearch, UUID> {
}