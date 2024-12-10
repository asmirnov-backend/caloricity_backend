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

    public static void quickSort(int[] arr, int l , int r) {
        if (l < r) {
            int partitionedIndex = partition(arr, l , r);

            quickSort(arr, l, partitionedIndex - 1);
            quickSort(arr, partitionedIndex + 1, r);
        }
    }

    private static int partition(int[] arr, int l, int r) {
        int pivot = arr[r];
        int i = (l-1);

        for (int j = l; j < r; j++) {
            if (arr[j] <= pivot) {
                i++;

                int swapTemp = arr[i];
                arr[i] = arr[j];
                arr[j] = swapTemp;
            }
        }

        int swapTemp = arr[i+1];
        arr[i+1] = arr[r];
        arr[r] = swapTemp;

        return i+1;
    }

}
