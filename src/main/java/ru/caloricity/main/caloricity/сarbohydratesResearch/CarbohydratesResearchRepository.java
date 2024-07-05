package ru.caloricity.main.caloricity.сarbohydratesResearch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(exported = false)
public interface CarbohydratesResearchRepository extends JpaRepository<CarbohydratesResearch, UUID> {
}