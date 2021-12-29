package com.zimonishim.tests;

import com.zimonishim.GUI.resultTables.resultTypes.ResultEntry;
import com.zimonishim.util.TestData;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class AddTests {

    public static Runnable addAllRunnable(Collection<Integer> collection, ITestCallback callback) {
        return addAllRunnable(collection, TestData.INTEGERS, callback);
    }

    public static Runnable addAllRunnable(Collection<Integer> collection, int[] dataCollection, ITestCallback callback) {
        return () -> {
            addAllTest(collection, Arrays.stream(dataCollection).boxed().collect(Collectors.toList()), callback);
            try {
                Thread.sleep(TestHandler.SLEEP_AMOUNT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Done executing ADD_ALL for " + collection.getClass().getSimpleName());
        };
    }

    public static void addAllTest(Collection<Integer> collection, Collection<Integer> numbers, ITestCallback callback) {

        try {
            collection = collection.getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        int[] copyOfUnaddedList = collection.stream().mapToInt(i -> i).toArray();

        long startTime = System.nanoTime();
        collection.addAll(numbers);
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;

        int[] copyOfAddedList = collection.stream().mapToInt(i -> i).toArray();
        callback.returnAddAllResult(new ResultEntry(collection.getClass().getSimpleName(), "Add All Numbers", copyOfUnaddedList, totalTime, copyOfAddedList));
    }
}
