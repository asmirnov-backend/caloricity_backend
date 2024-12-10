package ru.caloricity.probe.research.proteinsresearch;

import ru.caloricity.common.DefinedUuidIterator;
import ru.caloricity.probe.Probe;

import java.util.Iterator;
import java.util.UUID;

public class ProteinsResearchFactory {
    Iterator<UUID> uuidIterator = new DefinedUuidIterator();

    public ProteinsResearch createSimple() {
        var research = new ProteinsResearch();
        research.setId(uuidIterator.next());
        research.setTitrantVolumeParallelFirst(10.);
        research.setTitrantVolumeParallelSecond(20.);
        research.setMassNaveskiParallelFirst(10.);
        research.setMassNaveskiParallelSecond(10.);
        research.setCoefficient(0.95);
        research.setControlVolume(5.);
        return research;
    }

    public ProteinsResearch createSimple(Probe probe) {
        var research = createSimple();
        research.setProbe(probe);
        return research;
    }
}
