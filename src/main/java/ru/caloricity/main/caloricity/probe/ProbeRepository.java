package ru.caloricity.main.caloricity.probe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(exported = false)
interface ProbeRepository extends JpaRepository<Probe, UUID> {
}