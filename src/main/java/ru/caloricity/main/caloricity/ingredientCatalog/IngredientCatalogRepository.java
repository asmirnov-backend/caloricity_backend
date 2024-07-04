package ru.caloricity.main.caloricity.ingredientCatalog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.caloricity.main.caloricity.ingredientCatalog.dto.IngredientCatalogDto;
import ru.caloricity.main.caloricity.ingredientCatalog.dto.IngredientCatalogInPageDto;

import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource(exported = false)
public interface IngredientCatalogRepository extends JpaRepository<IngredientCatalog, UUID> {
    Page<IngredientCatalogInPageDto> findAllProjectedBy(Pageable pageable);

    Optional<IngredientCatalogDto> findDtoById(UUID id);
}