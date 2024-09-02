package ru.caloricity.drysubstancesresearch;

import ru.caloricity.common.DefinedUuidIterator;
import ru.caloricity.probe.Probe;

import java.util.Iterator;
import java.util.UUID;

public class DrySubstancesResearchFactory {
    Iterator<UUID> uuidIterator = new DefinedUuidIterator();

    public DrySubstancesResearch createSimple() {
        var research = new DrySubstancesResearch();
        research.setId(uuidIterator.next());
        research.setByuksaAfterDryingParallelFirst(50f);
        research.setByuksaAfterDryingParallelSecond(52f);
        research.setByuksaParallelFirst(11f);
        research.setByuksaParallelSecond(12f);
        return research;
    }

    public DrySubstancesResearch createSimple(Probe probe) {
        var research = createSimple();
        research.setProbe(probe);
        return research;
    }
}
