package com.zimonishim.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Contains different collections of data. This allows us to run tests without specifying what data to test.
 */
public class TestData {

    private static Number[] integers;

    public static void initLargeArray(){
        integers = setupLargeArray();
    }

    private static <T extends Number> T[] setupLargeArray(){
        System.out.println("Setting up large array.");

        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < 100000; i++){
            list.add(i);
        }

        Collections.shuffle(list);

        return (T[]) list.toArray(new Integer[0]);
    }

    public static <T extends Number> T[] getBigArray(){
        return (T[]) integers;
    }
}