package ru.caloricity.probe;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ProbeType {
    FIRST(0.012),
    SECOND(0.01),
    THIRD(0.001);


    public final double coefficientOfMinerals;
}
