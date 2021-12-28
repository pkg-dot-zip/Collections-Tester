package com.zimonishim.tests;

import com.zimonishim.GUI.IGUICallback;
import com.zimonishim.util.CollectionsContainer;
import com.zimonishim.util.TestData;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

public class TestRunner {

    //NOTE: https://gist.github.com/psayre23/c30a821239f4818b0709

    public static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor(); // Used to run all the tests on -> Responsible for starting other threads as well.
    public static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);

    public static synchronized void runAllTestsFromButton(TestHandler testHandler, IGUICallback callback) {
        EXECUTOR.submit(() -> TestRunner.runAllTests(testHandler, callback));
    }


    private static synchronized void runAllTests(TestHandler testHandler, IGUICallback guiCallback) {
        runAllTests(testHandler, guiCallback, 10);
    }

    private static synchronized void runAllTests(TestHandler testHandler, IGUICallback guiCallback, int amountOfRuns) {
        Collection<Runnable> runnables = new ArrayList<>();

        for (int i = 0; i < amountOfRuns; ++i) {
            runnables.addAll(getListTests(testHandler));
            runnables.addAll(getSetTests(testHandler));
        }

        // Submit runnable and add it to futures list.
        List<Future<?>> futures = runnables.stream()
                .map(THREAD_POOL_EXECUTOR::submit)
                .collect(Collectors.toList());


        // TODO: This is the reason why the application freezes for a bit. Fix this by not running this method in the main thread.
        // Don't move on until al tasks are done.
        boolean done = false;
        while (!done) {
            done = true;
            for (Future<?> future : futures) {
                if (!future.isDone()) {
                    done = false;
                }
            }
        }

        System.out.println("Done executing tasks");
        guiCallback.refresh(testHandler);
    }

    private static Collection<Runnable> getListTests(ITestCallback testHandler) {
        Collection<Runnable> runnables = new ArrayList<>();

        CollectionsContainer.getLists().forEach(l -> {
            List<Integer> list = null;

            try {
                list = l.getClass().newInstance();
            } catch (InstantiationException | IllegalAccessException instantiationException) {
                instantiationException.printStackTrace();
            }
            Runnable sortRunnable = SortingTests.sortRunnable(list, Comparator.naturalOrder(), testHandler);
            runnables.add(sortRunnable);

            list = null;
            try {
                list = l.getClass().newInstance();
            } catch (InstantiationException | IllegalAccessException instantiationException) {
                instantiationException.printStackTrace();
            }

            Runnable addAllRunnable = AddTests.addAllRunnable(list, testHandler);
            runnables.add(addAllRunnable);

            list = null;
            try {
                list = l.getClass().newInstance();
            } catch (InstantiationException | IllegalAccessException instantiationException) {
                instantiationException.printStackTrace();
            }

            list.addAll(Arrays.stream(TestData.getBigArray()).boxed().collect(Collectors.toList()));

            Runnable removeRunnable = RemoveTests.removeRunnable(list, testHandler);
            runnables.add(removeRunnable);
        });

        return runnables;
    }

    private static Collection<Runnable> getSetTests(ITestCallback testHandler) {
        Collection<Runnable> runnables = new ArrayList<>();

        CollectionsContainer.getSets().forEach(s -> {
            Set<Integer> set = null;

            try {
                set = s.getClass().newInstance();
            } catch (InstantiationException | IllegalAccessException instantiationException) {
                instantiationException.printStackTrace();
            }

            Runnable addAllThread = AddTests.addAllRunnable(set, testHandler);
            runnables.add(addAllThread);

            set = null;
            try {
                set = s.getClass().newInstance();
            } catch (InstantiationException | IllegalAccessException instantiationException) {
                instantiationException.printStackTrace();
            }

            set.addAll(Arrays.stream(TestData.getBigArray()).boxed().collect(Collectors.toList()));
            Runnable removeRunnable = RemoveTests.removeRunnable(set, testHandler);
            runnables.add(removeRunnable);
        });

        return runnables;
    }
}
