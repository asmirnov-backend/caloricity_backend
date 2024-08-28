package ru.caloricity.carbohydratesresearch;

import ru.caloricity.probe.Probe;

import java.util.UUID;

public class CarbohydratesResearchFactory {
    public CarbohydratesResearch createSimple() {
        var research = new CarbohydratesResearch();
        research.setId(UUID.randomUUID());
        research.setByuksaAfterDryingParallelFirst(50f);
        research.setByuksaAfterDryingParallelSecond(52f);
        research.setByuksaParallelFirst(11f);
        research.setByuksaParallelSecond(12f);
        return research;
    }

    public CarbohydratesResearch createSimple(Probe probe) {
        var research = createSimple();
        research.setProbe(probe);
        return research;
    }
}
