package ru.caloricity.probeingredient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource(exported = false)
public interface ProbeIngredientRepository extends JpaRepository<ProbeIngredient, UUID> {
    Optional<ProbeIngredientDto> findDtoById(UUID id);
}