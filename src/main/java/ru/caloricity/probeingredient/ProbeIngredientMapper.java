package ru.caloricity.probeingredient;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.caloricity.ingredient.IngredientMapperUtils;
import ru.caloricity.probe.ProbeMapperUtils;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {ProbeIngredientMapperUtils.class, ProbeMapperUtils.class, IngredientMapperUtils.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ProbeIngredientMapper {
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(source = "probeId", target = "probe", qualifiedByName = {"ProbeMapperUtils", "getReferenceById"})
    @Mapping(source = "ingredientId", target = "ingredient", qualifiedByName = {"IngredientMapperUtils", "getReferenceById"})
    ProbeIngredient toEntity(ProbeIngredientCreateDto dto);

    @Mapping(source = "ingredient.name", target = "ingredientName")
    @Mapping(source = ".", target = "water", qualifiedByName = {"ProbeIngredientMapperUtils", "calcWater"})
    @Mapping(source = ".", target = "proteins", qualifiedByName = {"ProbeIngredientMapperUtils", "calcProteins"})
    @Mapping(source = ".", target = "fats", qualifiedByName = {"ProbeIngredientMapperUtils", "calcFats"})
    @Mapping(source = ".", target = "carbohydrates", qualifiedByName = {"ProbeIngredientMapperUtils", "calcCarbohydrates"})
    ProbeIngredientInPageDto toPageDto(ProbeIngredient probeIngredient);
}
