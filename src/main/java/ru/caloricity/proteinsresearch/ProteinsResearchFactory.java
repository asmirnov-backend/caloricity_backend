package ru.caloricity.proteinsresearch;

import ru.caloricity.probe.Probe;

import java.util.UUID;

public class ProteinsResearchFactory {
    public ProteinsResearch createSimple() {
        var research = new ProteinsResearch();
        research.setId(UUID.randomUUID());
        research.setMass(100f);
        research.setTitrantVolume(8f);
        research.setCoefficient(0.95f);
        research.setControlVolume(5f);
        return research;
    }

    public ProteinsResearch createSimple(Probe probe) {
        var research = createSimple();
        research.setProbe(probe);
        return research;
    }
}
