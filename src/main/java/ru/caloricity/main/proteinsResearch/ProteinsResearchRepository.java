package ru.caloricity.main.proteinsResearch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(exported = false)
interface ProteinsResearchRepository extends JpaRepository<ProteinsResearch, UUID> {
}