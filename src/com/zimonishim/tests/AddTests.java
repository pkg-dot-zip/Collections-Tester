package com.zimonishim.tests;

import com.zimonishim.GUI.IGUICallback;
import com.zimonishim.GUI.resultTables.resultTypes.ResultEntry;

import java.util.Arrays;
import java.util.Collection;

import static com.zimonishim.util.TestData.getIntegerArray;

public class AddTests {

    public static <T extends Number> Thread addAllThread(Collection<T> collection, IGUICallback callback){
        return addAllThread(collection, Arrays.asList(getIntegerArray()), callback);
    }

    public static <T extends Number> Thread addAllThread(Collection<T> collection, Collection<T> dataCollection, IGUICallback callback){
        return new Thread(() -> {
            System.out.println("Clicked on testButton.");
            addAllTest(collection, dataCollection, callback);
            System.out.println("Starting thread for " + collection.getClass().getSimpleName() + ".");
        });
    }

    public static <T extends Number> void addAllTest(Collection<T> collection, Collection<T> numbers, IGUICallback callback){
        String listName = collection.getClass().getSimpleName();

        System.out.println("Now testing " + listName + ".");

        Number[] copyOfUnaddedList = collection.toArray(new Number[0]);

        long startTime = System.nanoTime();

        collection.addAll(numbers);

        long endTime = System.nanoTime();

        long totalTime = endTime - startTime;
        System.out.println("Total time for adding entries " + numbers.toString() + ": " + totalTime + " ns.");

        Number[] copyOfAddedList = collection.toArray(new Number[0]);

        callback.addSortResultsToGUI(new ResultEntry(listName, "Add All Numbers", copyOfUnaddedList, totalTime, copyOfAddedList));
    }
}