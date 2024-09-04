package ru.caloricity.ingredientcatalog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource(exported = false)
public interface IngredientCatalogRepository extends JpaRepository<IngredientCatalog, UUID> {
    Page<IngredientCatalogInPageDto> findAllDtoBy(Pageable pageable);

    Page<IngredientCatalogInPageDto> findAllByNameLikeIgnoreCase(Pageable pageable, String like);

    Optional<IngredientCatalogDto> findDtoById(UUID id);
}