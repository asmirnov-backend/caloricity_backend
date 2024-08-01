package ru.caloricity.ingredientCatalog;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IngredientCatalogMapper {
    IngredientCatalog toEntity(IngredientCatalogCreateDto dto);
}
