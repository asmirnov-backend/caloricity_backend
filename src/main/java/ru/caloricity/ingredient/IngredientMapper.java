package ru.caloricity.ingredient;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IngredientMapper {
    Ingredient toEntity(IngredientCreateDto dto);
}
