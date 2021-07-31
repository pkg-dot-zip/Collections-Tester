package com.zimonishim.tests;

import com.zimonishim.GUI.IGUICallback;
import com.zimonishim.util.CollectionsContainer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestRunner {

    public static ExecutorService executor = Executors.newFixedThreadPool(10); //This is public so we can setOnCloseRequest() in the Main class.

    public static synchronized void runAllTests(TestHandler testHandler, IGUICallback guiCallback){
        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < 10; ++i){
            Future<?> f = executor.submit(() -> {
                System.out.println("Running.");
                runListTests(testHandler);
                runSetTests(testHandler);
            });
            futures.add(f);
        }

        for(Future<?> future : futures) {
            System.out.println("Finished feature");

            try {
                future.get(); // This is a blocking call.
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        guiCallback.addAddAllResultsToGUI(testHandler.getAddAllResultEntryList());
        guiCallback.addSortResultsToGUI(testHandler.getSortingResultEntryList());
    }

    public static void runListTests(ITestCallback testHandler){
        CollectionsContainer.getLists().forEach(l -> {
            List<Integer> list = null;

            try {
                list = l.getClass().newInstance();
            } catch (InstantiationException | IllegalAccessException instantiationException) {
                instantiationException.printStackTrace();
            }
            Thread sortThread = SortingTests.sortThread(list, Comparator.naturalOrder(), testHandler);
            sortThread.start();
            try {
                sortThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            list = null;
            try {
                list = l.getClass().newInstance();
            } catch (InstantiationException | IllegalAccessException instantiationException) {
                instantiationException.printStackTrace();
            }

            Thread addAllThread = AddTests.addAllThread(list, testHandler);
            addAllThread.start();
            try {
                addAllThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public static void runSetTests(ITestCallback testHandler){
        CollectionsContainer.getSets().forEach(s -> {
            Set<Integer> set = null;

            try {
                set = s.getClass().newInstance();
            } catch (InstantiationException | IllegalAccessException instantiationException) {
                instantiationException.printStackTrace();
            }

            Thread addAllThread = AddTests.addAllThread(set, testHandler);
            addAllThread.start();
            try {
                addAllThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}