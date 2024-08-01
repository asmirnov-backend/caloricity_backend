package ru.caloricity.ingredient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientInPageDto {
    private UUID id;
    private Float gross;
    private Float net;
    private String name;
    private Float water;
    private Float proteins;
    private Float fats;
    private Float carbohydrates;
}
