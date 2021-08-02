package com.zimonishim.tests;

import com.zimonishim.GUI.resultTables.resultTypes.ResultEntry;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static com.zimonishim.util.TestData.getBigArray;

/**
 * Contains methods used to test sorting subclasses of the java.util.List interface with different comparators.
 */
public class SortingTests {

    public static Thread sortThread(List<Integer> list, Comparator<Integer> comparator, ITestCallback callback){
        return sortThread(list, getBigArray(), comparator, callback);
    }

    public static Thread sortThread(List<Integer> list, int[] dataArray, Comparator<Integer> comparator, ITestCallback callback){
        return new Thread(() -> {
            System.out.println("Clicked on testButton.");
            sortTest(list, Arrays.stream(dataArray).boxed().toArray(Integer[]::new), comparator, callback);
            System.out.println("Starting thread for " + list.getClass().getSimpleName() + ".");
        });
    }

    public static void sortTest(List<Integer> list, Integer[] dataArray, Comparator<Integer> comparator, ITestCallback callback){

        try {
            list = list.getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        String listName = list.getClass().getSimpleName();

        System.out.println("Now testing " + listName + ".");

        list.addAll(Arrays.asList(dataArray));

        int[] copyOfUnsortedList = list.stream().mapToInt(i -> i).toArray();

        long startTime = System.nanoTime();

        list.sort(comparator);

        long endTime = System.nanoTime();

        long totalTime = endTime - startTime;
        System.out.println("Total time for sorting: " + totalTime + " ns.");

        int[] copyOfSortedList = list.stream().mapToInt(i -> i).toArray();

        callback.returnSortResult(new ResultEntry(listName, "Sort Natural Order", copyOfUnsortedList, totalTime, copyOfSortedList));
    }
}