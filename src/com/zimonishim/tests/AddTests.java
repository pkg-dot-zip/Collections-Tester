package com.zimonishim.tests;

import com.zimonishim.GUI.resultTables.resultTypes.ResultEntry;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.zimonishim.util.TestData.getBigArray;

public class AddTests {

    public static Thread addAllThread(Collection<Integer> collection, ITestCallback callback){
        return addAllThread(collection, getBigArray(), callback);
    }

    public static Thread addAllThread(Collection<Integer> collection, int[] dataCollection, ITestCallback callback){
        return new Thread(() -> {
            System.out.println("Clicked on testButton.");
            addAllTest(collection, Arrays.stream(dataCollection).boxed().collect(Collectors.toList()), callback);
            System.out.println("Starting thread for " + collection.getClass().getSimpleName() + ".");
        });
    }

    public static void addAllTest(Collection<Integer> collection, Collection<Integer> numbers, ITestCallback callback){

        try {
            collection = collection.getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        String listName = collection.getClass().getSimpleName();

        System.out.println("Collections contains on init: " + collection + " for " + listName);

        System.out.println("Now testing " + listName + ".");

        int[] copyOfUnaddedList = collection.stream().mapToInt(i -> i).toArray();

        long startTime = System.nanoTime();

        collection.addAll(numbers);

        long endTime = System.nanoTime();

        long totalTime = endTime - startTime;

        int[] copyOfAddedList = collection.stream().mapToInt(i -> i).toArray();

        callback.returnAddAllResult(new ResultEntry(listName, "Add All Numbers", copyOfUnaddedList, totalTime, copyOfAddedList));
    }
}