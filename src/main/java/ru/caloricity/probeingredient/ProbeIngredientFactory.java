package ru.caloricity.probeingredient;

import ru.caloricity.common.DefinedUuidIterator;
import ru.caloricity.ingredient.Ingredient;
import ru.caloricity.probe.Probe;

import java.util.Iterator;
import java.util.UUID;

public class ProbeIngredientFactory {
    Iterator<UUID> uuidIterator = new DefinedUuidIterator();

    public ProbeIngredient createSimple(Probe probe, Ingredient ingredient) {
        ProbeIngredient entity = new ProbeIngredient();
        entity.setId(uuidIterator.next());
        entity.setNet(50f);
        entity.setGross(60f);
        entity.setIngredient(ingredient);
        entity.setProbe(probe);
        return entity;
    }
}
