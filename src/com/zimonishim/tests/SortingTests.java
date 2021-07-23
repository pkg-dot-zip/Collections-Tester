package com.zimonishim.tests;

import com.zimonishim.GUI.IGUICallback;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SortingTests {

    public static Thread sortThread(List<Integer> integers, Comparator comparator, IGUICallback callback){
        return new Thread(() -> {
            System.out.println("Clicked on testButton.");
            sortTest(integers, comparator, callback);
            System.out.println("Starting thread for " + integers.getClass().getSimpleName() + ".");
        });
    }

    public static void sortTest(List<Integer> integers, Comparator comparator, IGUICallback callback){
        String listName = integers.getClass().getSimpleName();

        System.out.println("Now testing " + listName + ".");

        integers.addAll(Arrays.asList(
                10,
                22,
                5,
                6,
                3,
                3,
                21,
                22,
                50,
                60,
                5
        ));

        long startTime = System.nanoTime();
        System.out.println(startTime);

        integers.sort(comparator);

        long endTime = System.nanoTime();
        System.out.println(endTime);

        long totalTime = endTime - startTime;
        System.out.println("Total time for sorting: " + totalTime + " ns.");

        callback.addResultToGUI("Total time for sorting: " + totalTime + " ns.");
    }

}