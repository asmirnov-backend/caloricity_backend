package ru.caloricity.fatsResearch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(exported = false)
interface FatsResearchRepository extends JpaRepository<FatsResearch, UUID> {
}