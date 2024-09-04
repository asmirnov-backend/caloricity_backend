package ru.caloricity.common;

import java.util.Iterator;
import java.util.UUID;

/**
 * Iterator for obtaining predefined UUIDs at any time
 */
public class DefinedUuidIterator implements Iterator<UUID> {
    Integer number = 999;

    @Override
    public boolean hasNext() {
        return number < 9998;
    }

    @Override
    public UUID next() {
        number++;
        return UUID.fromString("70fcd407-0a59-4c4e-8b05-d9b67e2a" + number);
    }
}
