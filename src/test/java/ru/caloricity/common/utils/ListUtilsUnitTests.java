package ru.caloricity.common.utils;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListUtilsUnitTests {
    @Test
    void equalsWithDefaultSort() throws Throwable {
        List<Integer> arr = Stream.of(5, 3, 2, 1, 4, 5, 6, 12, -2, 0).collect(Collectors.toList());
        List<Integer> sorted = ListUtils.quickSort(arr);
        Collections.sort(arr);
        assertEquals(arr, sorted);
    }
}
