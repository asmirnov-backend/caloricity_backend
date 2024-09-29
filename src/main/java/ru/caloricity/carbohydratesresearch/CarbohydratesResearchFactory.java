package ru.caloricity.carbohydratesresearch;

import ru.caloricity.common.DefinedUuidIterator;
import ru.caloricity.probe.Probe;

import java.util.Iterator;
import java.util.UUID;

public class CarbohydratesResearchFactory {
    Iterator<UUID> uuidIterator = new DefinedUuidIterator();

    public CarbohydratesResearch createSimple() {
        var research = new CarbohydratesResearch();
        research.setId(uuidIterator.next());
        research.setByuksaAfterDryingParallelFirst(50f);
        research.setByuksaAfterDryingParallelSecond(52f);
        research.setByuksaParallelFirst(60f);
        research.setByuksaParallelSecond(61f);
        return research;
    }

    public CarbohydratesResearch createSimple(Probe probe) {
        var research = createSimple();
        research.setProbe(probe);
        return research;
    }
}
