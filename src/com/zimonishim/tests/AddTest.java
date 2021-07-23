package com.zimonishim.tests;

import com.zimonishim.GUI.IGUICallback;

import java.util.Arrays;
import java.util.Collection;

import static com.zimonishim.util.TestData.getIntegerArray;

public class AddTest {

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

        Object[] copyOfUnaddedList = collection.toArray();

        long startTime = System.nanoTime();

        collection.addAll(numbers);

        long endTime = System.nanoTime();

        long totalTime = endTime - startTime;
        System.out.println("Total time for adding entries " + numbers.toString() + ": " + totalTime + " ns.");

        Object[] copyOfAddedList = collection.toArray();

        callback.addAddAllResultsToGUI("Total time for adding " + numbers.toString() + " to " + listName + ": " + totalTime + " ns." + "\n" + Arrays.toString(copyOfUnaddedList) + "\n" + Arrays.toString(copyOfAddedList));
    }
}