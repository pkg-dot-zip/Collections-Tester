package com.zimonishim.tests;

import com.zimonishim.GUI.IGUICallback;
import com.zimonishim.GUI.resultTables.resultTypes.ResultEntry;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static com.zimonishim.util.TestData.getIntegerArray;

/**
 * Contains methods used to test sorting subclasses of the java.util.List interface with different comparators.
 */
public class SortingTests {

    public static <T extends Number> Thread sortThread(List<T> list, Comparator<T> comparator, IGUICallback callback){
        return sortThread(list, getIntegerArray(), comparator, callback);
    }

    public static <T extends Number> Thread sortThread(List<T> list, T[] dataArray, Comparator<T> comparator, IGUICallback callback){
        return new Thread(() -> {
            System.out.println("Clicked on testButton.");
            sortTest(list, dataArray, comparator, callback);
            System.out.println("Starting thread for " + list.getClass().getSimpleName() + ".");
        });
    }

    public static <T extends Number> void sortTest(List<T> list, T[] dataArray, Comparator<T> comparator, IGUICallback callback){
        String listName = list.getClass().getSimpleName();

        System.out.println("Now testing " + listName + ".");

        list.addAll(Arrays.asList(dataArray));

        Number[] copyOfUnsortedList = list.toArray(new Number[0]);

        long startTime = System.nanoTime();

        list.sort(comparator);

        long endTime = System.nanoTime();

        long totalTime = endTime - startTime;
        System.out.println("Total time for sorting: " + totalTime + " ns.");

        Number[] copyOfSortedList = list.toArray(new Number[0]);

        System.out.println("Total time for sorting " + listName + ": " + totalTime + " ns." + "\n" + Arrays.toString(copyOfUnsortedList) + "\n" + Arrays.toString(copyOfSortedList));

        callback.addSortResultsToGUI(new ResultEntry(listName, "Sort Natural Order", copyOfUnsortedList, totalTime, copyOfSortedList));
    }
}