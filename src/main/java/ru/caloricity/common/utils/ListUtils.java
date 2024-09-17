package ru.caloricity.common.utils;

import java.util.LinkedList;
import java.util.List;

public class ListUtils {

    /**
     * Just for save this implementation of quickSort
     */
    public static List<Integer> quickSort(List<Integer> arr) {
        if (arr.size() <= 1) {
            return arr;
        }

        int piv = arr.get(arr.size() / 2);
        List<Integer> mid = new LinkedList<>();
        List<Integer> left = new LinkedList<>();
        List<Integer> right = new LinkedList<>();

        for (int el : arr) {
            if (el > piv) {
                right.add(el);
            } else if (el < piv) {
                left.add(el);
            } else {
                mid.add(el);
            }
        }

        List<Integer> partSorted = new LinkedList<>();
        partSorted.addAll(quickSort(left));
        partSorted.addAll(mid);
        partSorted.addAll(quickSort(right));

        return partSorted;
    }

}
