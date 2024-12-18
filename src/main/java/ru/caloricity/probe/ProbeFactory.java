package ru.caloricity.probe;

import ru.caloricity.common.DefinedUuidIterator;

import java.util.Iterator;
import java.util.UUID;

public class ProbeFactory {
    Iterator<UUID> uuidIterator = new DefinedUuidIterator();

    public Probe createSimple() {
        Probe probe = new Probe();
        probe.setId(uuidIterator.next());
        probe.setName("Test name");
        probe.setCode("f123213");
        probe.setType(ProbeType.FIRST);
        probe.setBankaEmptyMass(50.);
        probe.setBankaWithProbeMass(60.);
        probe.setMassTheory(1.);
        return probe;
    }
}
