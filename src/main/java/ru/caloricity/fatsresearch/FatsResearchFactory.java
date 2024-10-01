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
        research.setPatronMassBeforeExtractionParallelFirst(80f);
        research.setPatronMassAfterExtractionParallelFirst(11f);
        research.setPatronMassBeforeExtractionParallelSecond(90f);
        research.setPatronMassAfterExtractionParallelSecond(12f);
        return research;
    }

    public FatsResearch createSimple(Probe probe) {
        var research = createSimple();
        research.setProbe(probe);
        return research;
    }
}
