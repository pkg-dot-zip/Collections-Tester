package com.zimonishim.tests;

import com.zimonishim.GUI.resultTables.resultTypes.ResultEntry;

import java.util.Arrays;
import java.util.Collection;

import static com.zimonishim.util.TestData.getBigArray;

public class AddTests {

    public static <T extends Number> Thread addAllThread(Collection<T> collection, ITestCallback callback){
        return addAllThread(collection, Arrays.asList(getBigArray()), callback);
    }

    public static <T extends Number> Thread addAllThread(Collection<T> collection, Collection<T> dataCollection, ITestCallback callback){
        return new Thread(() -> {
            System.out.println("Clicked on testButton.");
            addAllTest(collection, dataCollection, callback);
            System.out.println("Starting thread for " + collection.getClass().getSimpleName() + ".");
        });
    }

    public static <T extends Number> void addAllTest(Collection<T> collection, Collection<T> numbers, ITestCallback callback){

        try {
            collection = collection.getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        String listName = collection.getClass().getSimpleName();

        System.out.println("Collections contains on init: " + collection + " for " + listName);

        System.out.println("Now testing " + listName + ".");

        Number[] copyOfUnaddedList = collection.toArray(new Number[0]);

        long startTime = System.nanoTime();

        collection.addAll(numbers);

        long endTime = System.nanoTime();

        long totalTime = endTime - startTime;

        Number[] copyOfAddedList = collection.toArray(new Number[0]);

        callback.returnAddAllResult(new ResultEntry(listName, "Add All Numbers", copyOfUnaddedList, totalTime, copyOfAddedList));
    }
}