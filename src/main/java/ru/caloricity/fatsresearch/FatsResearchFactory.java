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
        research.setPatronMassAfterExtractionParallelFirst(11f);
        research.setPatronMassBeforeExtractionParallelFirst(8f);
        research.setPatronMassAfterExtractionParallelSecond(12f);
        research.setPatronMassBeforeExtractionParallelSecond(9f);
        return research;
    }

    public FatsResearch createSimple(Probe probe) {
        var research = createSimple();
        research.setProbe(probe);
        return research;
    }
}
