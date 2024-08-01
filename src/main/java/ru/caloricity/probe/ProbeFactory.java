package ru.caloricity.probe;

import java.util.UUID;

public class ProbeFactory {
    public Probe createSimple() {
        Probe probe = new Probe();
        probe.setId(UUID.randomUUID());
        probe.setName("Test name");
        probe.setCode("f123213");
        probe.setType(ProbeType.FIRST);
        probe.setMassFact(1f);
        probe.setMassTheory(1f);
        return probe;
    }
}
