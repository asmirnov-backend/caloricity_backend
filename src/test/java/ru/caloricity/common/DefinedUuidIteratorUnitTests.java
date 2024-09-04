package ru.caloricity.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DefinedUuidIteratorUnitTests {
    @Test
    public void iteratorReturnAlwaysTheSameValues() {
        DefinedUuidIterator iterator1 = new DefinedUuidIterator();
        DefinedUuidIterator iterator2 = new DefinedUuidIterator();

        for (int i = 0; i < 9000; i++) {
            Assertions.assertEquals(iterator1.next(), iterator2.next());
        }
    }
}
