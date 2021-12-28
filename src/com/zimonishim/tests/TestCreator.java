package com.zimonishim.tests;

import com.zimonishim.util.TestData;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TestCreator {

    public static Runnable createSortTest(List l, Comparator<Integer> comparator, ITestCallback testHandler) {
        List<Integer> list = null;

        try {
            list = l.getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException instantiationException) {
            instantiationException.printStackTrace();
        }

        return SortingTests.sortRunnable(list, comparator, testHandler);
    }

    public static Runnable createAddAllTest(Collection collection, ITestCallback testHandler) {
        Collection<Integer> o = null;
        try {
            o = collection.getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException instantiationException) {
            instantiationException.printStackTrace();
        }

        return AddTests.addAllRunnable(o, testHandler);
    }

    public static Runnable createRemoveTest(Collection collection, ITestCallback testHandler) {
        Collection<Integer> collection1 = null;

        try {
            collection1 = collection.getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        collection1.addAll(Arrays.stream(TestData.getBigArray()).boxed().collect(Collectors.toList()));
        return RemoveTests.removeRunnable(collection1, testHandler);
    }
}
