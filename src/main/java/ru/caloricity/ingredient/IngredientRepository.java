package ru.caloricity.ingredient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(exported = false)
interface IngredientRepository extends JpaRepository<Ingredient, UUID> {
    Page<IngredientInPageDto> findAllDtoBy(Pageable pageable);

}