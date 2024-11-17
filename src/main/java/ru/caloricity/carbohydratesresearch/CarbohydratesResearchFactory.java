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
        research.setByuksaAfterDryingParallelFirst(50.);
        research.setByuksaAfterDryingParallelSecond(52.);
        research.setByuksaParallelFirst(60.);
        research.setByuksaParallelSecond(61.);
        return research;
    }

    public CarbohydratesResearch createSimple(Probe probe) {
        var research = createSimple();
        research.setProbe(probe);
        return research;
    }
}
