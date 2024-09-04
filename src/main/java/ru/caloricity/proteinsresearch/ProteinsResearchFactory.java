package ru.caloricity.proteinsresearch;

import ru.caloricity.common.DefinedUuidIterator;
import ru.caloricity.probe.Probe;

import java.util.Iterator;
import java.util.UUID;

public class ProteinsResearchFactory {
    Iterator<UUID> uuidIterator = new DefinedUuidIterator();

    public ProteinsResearch createSimple() {
        var research = new ProteinsResearch();
        research.setId(uuidIterator.next());
        research.setTitrantVolumeParallelFirst(10f);
        research.setTitrantVolumeParallelSecond(20f);
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
