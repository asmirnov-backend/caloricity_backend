package ru.caloricity.drySubstancesResearch;

import ru.caloricity.probe.Probe;

import java.util.UUID;

public class DrySubstancesResearchFactory {
    public DrySubstancesResearch createSimple() {
        var drySubstancesResearch = new DrySubstancesResearch();
        drySubstancesResearch.setId(UUID.randomUUID());
        drySubstancesResearch.setMass(100f);
        drySubstancesResearch.setBankaEmptyMass(50f);
        drySubstancesResearch.setByuksaParallelFirst(11f);
        drySubstancesResearch.setBankaWithProbeMass(10f);
        drySubstancesResearch.setByuksaParallelSecond(12f);
        return drySubstancesResearch;
    }

    public DrySubstancesResearch createSimple(Probe probe) {
        var drySubstancesResearch = createSimple();
        drySubstancesResearch.setProbe(probe);
        return drySubstancesResearch;
    }
}
