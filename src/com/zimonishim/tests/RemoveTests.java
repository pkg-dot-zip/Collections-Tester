package com.zimonishim.tests;

import com.zimonishim.GUI.resultTables.resultTypes.ResultEntry;

import java.util.Arrays;
import java.util.Collection;

import static com.zimonishim.util.TestData.getRemoveArray;

public class RemoveTests {

    public static <T extends Number> Thread removeThread(Collection<T> collection, ITestCallback callback){
        return removeThread(collection, Arrays.asList(getRemoveArray()), callback);
    }

    public static <T extends Number> Thread removeThread(Collection<T> collection, Collection<T> numbersToRemove, ITestCallback callback){
        return new Thread(() -> {
            System.out.println("Clicked on testButton.");
            removeTest(collection, numbersToRemove, callback);
            System.out.println("Starting thread for " + collection.getClass().getSimpleName() + ".");
        });
    }

    public static <T extends Number> void removeTest(Collection<T> collection, Collection<T> numbersToRemove, ITestCallback callback){

        String listName = collection.getClass().getSimpleName();

        System.out.println("Collections contains on init: " + collection + " for " + listName);

        System.out.println("Now testing " + listName + ".");

        Number[] copyOfInitialList = collection.toArray(new Number[0]);

        long startTime = System.nanoTime();

        numbersToRemove.forEach(collection::remove);

        long endTime = System.nanoTime();

        long totalTime = endTime - startTime;

        Number[] copyOfRemovedList = collection.toArray(new Number[0]);

        callback.returnRemoveResult(new ResultEntry(listName, "Remove Numbers", copyOfInitialList, totalTime, copyOfRemovedList));
    }
}