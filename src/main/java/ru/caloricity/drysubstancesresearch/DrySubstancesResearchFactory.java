package ru.caloricity.drysubstancesresearch;

import ru.caloricity.probe.Probe;

import java.util.UUID;

public class DrySubstancesResearchFactory {
    public DrySubstancesResearch createSimple() {
        var research = new DrySubstancesResearch();
        research.setId(UUID.randomUUID());
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
