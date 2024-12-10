package ru.caloricity.common.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AverageUnitTest {

    @Test
    void calc() {
        double res = new Average(1.0, 2.0).calc();
        assertEquals(1.5, res);
    }
}