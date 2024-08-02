package ru.caloricity.ingredient;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.caloricity.probe.ProbeMapperUtils;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {IngredientMapperUtils.class, ProbeMapperUtils.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface IngredientMapper {
    @Mapping(target="id", expression="java(java.util.UUID.randomUUID())")
    @Mapping(source = "ingredientInCatalogId", target = "ingredientInCatalog", qualifiedByName = {"IngredientMapperUtils", "mapIngredientInCatalogIdToEntity"})
    @Mapping(source = "probeId", target = "probe", qualifiedByName = {"ProbeMapperUtils", "mapProbeIdToEntity"})
    Ingredient toEntity(IngredientCreateDto dto);

    @Mapping(source = "ingredientInCatalog.name", target = "name")
    @Mapping(source = ".", target = "water", qualifiedByName = {"IngredientMapperUtils", "calcWater"})
    @Mapping(source = ".", target = "proteins", qualifiedByName = {"IngredientMapperUtils", "calcProteins"})
    @Mapping(source = ".", target = "fats", qualifiedByName = {"IngredientMapperUtils", "calcFats"})
    @Mapping(source = ".", target = "carbohydrates", qualifiedByName = {"IngredientMapperUtils", "calcCarbohydrates"})
    IngredientInPageDto toPageDto(Ingredient entity);
}
