package ru.caloricity.main.carbohydratesResearch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(exported = false)
interface CarbohydratesResearchRepository extends JpaRepository<CarbohydratesResearch, UUID> {
}