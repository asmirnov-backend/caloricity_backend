package ru.caloricity.fatsresearch;

import ru.caloricity.common.DefinedUuidIterator;
import ru.caloricity.probe.Probe;

import java.util.Iterator;
import java.util.UUID;

public class FatsResearchFactory {
    Iterator<UUID> uuidIterator = new DefinedUuidIterator();

    public FatsResearch createSimple() {
        var research = new FatsResearch();
        research.setId(uuidIterator.next());
        research.setPatronMassBeforeExtractionParallelFirst(80.);
        research.setPatronMassAfterExtractionParallelFirst(11.);
        research.setPatronMassBeforeExtractionParallelSecond(90.);
        research.setPatronMassAfterExtractionParallelSecond(12.);
        research.setMassNaveskiParallelFirst(10.);
        research.setMassNaveskiParallelSecond(10.);
        return research;
    }

    public FatsResearch createSimple(Probe probe) {
        var research = createSimple();
        research.setProbe(probe);
        return research;
    }
}
