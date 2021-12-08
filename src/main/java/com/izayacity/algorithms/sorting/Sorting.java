package com.izayacity.algorithms.sorting;
import java.util.*;

public class Sorting {

    public static List<Integer> sort(List<Integer> list) {
        return mergeSort(list);
    }

    public static List<Integer> mergeSort(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            return list;
        }
        return mergeSortRecur(list, 0, list.size() - 1);
    }

    private static List<Integer> mergeSortRecur(List<Integer> list, int start, int end) {
        if (start >= end) {
            return list;
        }
        int mid = start + (end - start) / 2;
        mergeSortRecur(list, start, mid);
        mergeSortRecur(list, mid + 1, end);
        return merge(list, start, end, mid);
    }

    private static List<Integer> merge(List<Integer> list, int start, int end, int mid) {
        int j = mid + 1;
        int i = start;
        List<Integer> result = new ArrayList<>();

        while (i <= mid && j <= end) {
            if (list.get(i) < list.get(j)) {
                result.add(list.get(i++));
            } else {
                result.add(list.get(j++));
            }
        }
        while (i <= mid) {
            result.add(list.get(i++));
        }
        while (j <= end) {
            result.add(list.get(j++));
        }
        int c = 0;
        for (int p = start; p <= end; p++) {
            list.set(p, result.get(c++));
        }
        return list;
    }

    public static List<Integer> quickSort(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            return list;
        }
        return quickSortRecur(list, 0, list.size() - 1);
    }

    private static List<Integer> quickSortRecur(List<Integer> list, int start, int end) {
        if (start >= end) {
            return list;
        }
        int pivot = partitionQS(list, start, end);
        quickSortRecur(list, start, pivot - 1);
        quickSortRecur(list, pivot, end);
        return list;
    }

    private static int partitionQS(List<Integer> list, int start, int end) {
        int pVal = list.get(start + (end - start) / 2);
        int i = start, j = end;

        while (i <= j) {
            while (list.get(i) < pVal) {
                i++;
            }
            while (list.get(j) > pVal) {
                j--;
            }
            if (i > j) {
                break;
            }
            Collections.swap(list, i, j);
            i++;
            j--;
        }
        return i;
    }

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(3, 1, 2);
        List<Integer> resList = sort(list);
        System.out.println(resList);

        list = Arrays.asList(5, 3, 1, 4, 2);
        resList = sort(list);
        System.out.println(resList);

        list = Arrays.asList(6, 19, 3, 12, 23, 5, 8, 1, 4);
        resList = sort(list);
        System.out.println(resList);
    }
}
