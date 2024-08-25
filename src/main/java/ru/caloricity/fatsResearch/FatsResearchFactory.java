package ru.caloricity.fatsResearch;

import ru.caloricity.probe.Probe;

import java.util.UUID;

public class FatsResearchFactory {
    public FatsResearch createSimple() {
        var research = new FatsResearch();
        research.setId(UUID.randomUUID());
        research.setPatronMassBeforeExtraction(11f);
        research.setPatronMassAfterExtraction(8f);
        return research;
    }

    public FatsResearch createSimple(Probe probe) {
        var research = createSimple();
        research.setProbe(probe);
        return research;
    }
}
