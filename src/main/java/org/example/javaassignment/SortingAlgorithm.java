package org.example.javaassignment;

import java.util.ArrayList;
import java.util.Map;

public class SortingAlgorithm {
    public static ArrayList<Integer> insertionSort(Map<Integer, Double> data){
        ArrayList<Integer> keys = new ArrayList<>(data.keySet());

        for (int i = 1; i < keys.size(); i++) {
            int key = keys.get(i);
            double value = data.get(key);
            int j = i - 1;

            while (j >= 0 && data.get(keys.get(j)) > value) {
                keys.set(j + 1, keys.get(j));
                j--;
            }
            keys.set(j + 1, key);
        }

        return keys;
    }
    public static ArrayList<Integer> mergeSort(Map<Integer, Double> data) {
        ArrayList<Integer> keys = new ArrayList<>(data.keySet());
        mergeSortHelper(data, keys, 0, keys.size() - 1);
        return keys;
    }

    private static void mergeSortHelper(Map<Integer, Double> data, ArrayList<Integer> keys, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;
            mergeSortHelper(data, keys, left, middle);
            mergeSortHelper(data, keys, middle + 1, right);
            merge(data, keys, left, middle, right);
        }
    }

    private static void merge(Map<Integer, Double> data, ArrayList<Integer> keys, int left, int middle, int right) {
        ArrayList<Integer> leftKeys = new ArrayList<>(keys.subList(left, middle + 1));
        ArrayList<Integer> rightKeys = new ArrayList<>(keys.subList(middle + 1, right + 1));

        int i = 0, j = 0, k = left;
        while (i < leftKeys.size() && j < rightKeys.size()) {
            if (data.get(leftKeys.get(i)) <= data.get(rightKeys.get(j))) {
                keys.set(k++, leftKeys.get(i++));
            } else {
                keys.set(k++, rightKeys.get(j++));
            }
        }
        while (i < leftKeys.size()) {
            keys.set(k++, leftKeys.get(i++));
        }
        while (j < rightKeys.size()) {
            keys.set(k++, rightKeys.get(j++));
        }
    }

    public static ArrayList<Integer> heapSort(Map<Integer, Double> data) {
        ArrayList<Integer> keys = new ArrayList<>(data.keySet());
        int n = keys.size();

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(data, keys, n, i);
        }

        for (int i = n - 1; i > 0; i--) {
            int temp = keys.get(0);
            keys.set(0, keys.get(i));
            keys.set(i, temp);
            heapify(data, keys, i, 0);
        }

        return keys;
    }

    private static void heapify(Map<Integer, Double> data, ArrayList<Integer> keys, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && data.get(keys.get(left)) > data.get(keys.get(largest))) {
            largest = left;
        }

        if (right < n && data.get(keys.get(right)) > data.get(keys.get(largest))) {
            largest = right;
        }

        if (largest != i) {
            int swap = keys.get(i);
            keys.set(i, keys.get(largest));
            keys.set(largest, swap);
            heapify(data, keys, n, largest);
        }
    }

    public static ArrayList<Integer> quickSort(Map<Integer, Double> data) {
        ArrayList<Integer> keys = new ArrayList<>(data.keySet());
        quickSortHelper(data, keys, 0, keys.size() - 1);
        return keys;
    }

    private static void quickSortHelper(Map<Integer, Double> data, ArrayList<Integer> keys, int low, int high) {
        if (low < high) {
            int pi = partition(data, keys, low, high);
            quickSortHelper(data, keys, low, pi - 1);
            quickSortHelper(data, keys, pi + 1, high);
        }
    }

    private static int partition(Map<Integer, Double> data, ArrayList<Integer> keys, int low, int high) {
        double pivot = data.get(keys.get(high));
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (data.get(keys.get(j)) <= pivot) {
                i++;
                int temp = keys.get(i);
                keys.set(i, keys.get(j));
                keys.set(j, temp);
            }
        }

        int temp = keys.get(i + 1);
        keys.set(i + 1, keys.get(high));
        keys.set(high, temp);

        return i + 1;
    }

    public static ArrayList<Integer> shellSort(Map<Integer, Double> data) {
        ArrayList<Integer> keys = new ArrayList<>(data.keySet());
        int n = keys.size();

        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int temp = keys.get(i);
                double value = data.get(temp);
                int j;
                for (j = i; j >= gap && data.get(keys.get(j - gap)) > value; j -= gap) {
                    keys.set(j, keys.get(j - gap));
                }
                keys.set(j, temp);
            }
        }

        return keys;
    }


}
