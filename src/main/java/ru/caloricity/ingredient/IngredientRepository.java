package ru.caloricity.ingredient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource(exported = false)
public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {
    Page<IngredientInPageDto> findAllDtoBy(Pageable pageable);

    Page<IngredientInPageDto> findAllByNameLikeIgnoreCase(Pageable pageable, String like);

    Optional<IngredientDto> findDtoById(UUID id);
}