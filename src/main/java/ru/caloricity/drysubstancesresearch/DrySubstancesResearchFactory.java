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
        research.setByuksaAfterDryingParallelFirst(50.);
        research.setByuksaAfterDryingParallelSecond(52.);
        research.setByuksaParallelFirst(61.);
        research.setByuksaParallelSecond(62.);
        return research;
    }

    public DrySubstancesResearch createSimple(Probe probe) {
        var research = createSimple();
        research.setProbe(probe);
        return research;
    }
}
