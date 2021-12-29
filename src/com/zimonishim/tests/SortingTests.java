package com.zimonishim.tests;

import com.zimonishim.GUI.resultTables.resultTypes.ResultEntry;
import com.zimonishim.util.TestData;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Contains methods used to test sorting subclasses of the java.util.List interface with different comparators.
 */
public class SortingTests {

    public static Runnable sortRunnable(List<Integer> list, Comparator<Integer> comparator, ITestCallback callback) {
        return sortRunnable(list, TestData.INTEGERS, comparator, callback);
    }

    public static Runnable sortRunnable(List<Integer> list, int[] dataArray, Comparator<Integer> comparator, ITestCallback callback) {
        return () -> {
            sortTest(list, Arrays.stream(dataArray).boxed().toArray(Integer[]::new), comparator, callback);
            try {
                Thread.sleep(TestHandler.SLEEP_AMOUNT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Done executing SORT for " + list.getClass().getSimpleName());
        };
    }

    public static void sortTest(List<Integer> list, Integer[] dataArray, Comparator<Integer> comparator, ITestCallback callback) {

        try {
            list = list.getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        list.addAll(Arrays.asList(dataArray));
        int[] copyOfUnsortedList = list.stream().mapToInt(i -> i).toArray();

        long startTime = System.nanoTime();
        list.sort(comparator);
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;

        int[] copyOfSortedList = list.stream().mapToInt(i -> i).toArray();
        callback.returnSortResult(new ResultEntry(list.getClass().getSimpleName(), comparator.getClass().getSimpleName(), copyOfUnsortedList, totalTime, copyOfSortedList));
    }
}
