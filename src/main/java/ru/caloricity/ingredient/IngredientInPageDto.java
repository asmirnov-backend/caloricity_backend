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
    private float gross;
    private float net;
    private String name;
}
