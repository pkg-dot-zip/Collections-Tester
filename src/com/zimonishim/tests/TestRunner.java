package com.zimonishim.tests;

import com.zimonishim.GUI.callbacks.IGUICallback;
import com.zimonishim.GUI.callbacks.IListSelectionCallback;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

public class TestRunner {

    //NOTE: https://gist.github.com/psayre23/c30a821239f4818b0709

    public static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor(); // Used to run all the tests on -> Responsible for starting other threads as well.
    public static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);

    public static synchronized boolean runAllTestsFromButton(TestHandler testHandler, IGUICallback callback, IListSelectionCallback selectionCallback) {
        // If nothing is selected we return false before executing any tests.
        if (selectionCallback.getSelectedSets().isEmpty() && selectionCallback.getSelectedSets().isEmpty()) {
            return false;
        }


        // Else we submit the tests to the executor and return true.
        EXECUTOR.submit(() -> TestRunner.runAllTests(testHandler, callback, selectionCallback));
        return true;
    }

    private static synchronized void runAllTests(TestHandler testHandler, IGUICallback guiCallback, IListSelectionCallback selectionCallback) {
        runAllTests(testHandler, guiCallback, selectionCallback, 10);
    }

    private static synchronized void runAllTests(TestHandler testHandler, IGUICallback guiCallback, IListSelectionCallback selectionCallback, final int amountOfRuns) {
        Collection<Runnable> runnables = new ArrayList<>();

        for (int i = 0; i < amountOfRuns; ++i) {
            runnables.addAll(getListTests(testHandler, selectionCallback));
            runnables.addAll(getSetTests(testHandler, selectionCallback));
        }

        // Submit runnable and add it to futures list.
        List<Future<?>> futures = runnables.stream()
                .map(THREAD_POOL_EXECUTOR::submit)
                .collect(Collectors.toList());

        int maxProgress = futures.size();

        // Don't move on until all tasks are done & update progressbar.
        boolean done = false;
        while (!done) {
            int currentProgress = 0;

            done = true;
            for (Future<?> future : futures) {
                if (!future.isDone()) {
                    done = false;
                } else {
                    currentProgress++;
                }
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            guiCallback.setProgress((double) currentProgress / maxProgress);
        }

        System.out.println("Done executing tasks");
        guiCallback.refresh(testHandler);
    }

    private static Collection<Runnable> getListTests(ITestCallback testHandler, IListSelectionCallback selectionCallback) {
        Collection<Runnable> runnables = new ArrayList<>();

        selectionCallback.getSelectedLists().forEach(l -> {

            for (Comparator comparator : getComparatorsForSortingTests()) {
                runnables.add(TestCreator.createSortTest(l, comparator, testHandler));
            }

            runnables.add(TestCreator.createAddAllTest(l, testHandler));
            runnables.add(TestCreator.createRemoveTest(l, testHandler));
        });

        return runnables;
    }

    private static Comparator[] getComparatorsForSortingTests() {
        return new Comparator[]{
                Comparator.naturalOrder(),
                Comparator.reverseOrder()
        };
    }

    private static Collection<Runnable> getSetTests(ITestCallback testHandler, IListSelectionCallback selectionCallback) {
        Collection<Runnable> runnables = new ArrayList<>();

        selectionCallback.getSelectedSets().forEach(s -> {
            runnables.add(TestCreator.createAddAllTest(s, testHandler));
            runnables.add(TestCreator.createRemoveTest(s, testHandler));
        });

        return runnables;
    }
}
