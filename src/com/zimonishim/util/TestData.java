package com.zimonishim.util;

import java.util.*;

/**
 * Contains different collections of data. This allows us to run tests without specifying what data to test.
 */
public class TestData {

    private static Number[] integers; //Used as default collection content if none is specified.
    private static Number[] toRemove; //Used ONLY by the RemoveTest.

    public static void initLargeArray(){
        integers = setupLargeArray();
    }

    private static <T extends Number> T[] setupLargeArray(){
        Random random = new Random();

        System.out.println("Setting up large array.");

        List<Integer> list = new ArrayList<>();
        Set<Integer> set = new HashSet<>();

        for (int i = 0; i < 100000; i++){

            //2.5 chance that we add it to the set of random integers.
            double rolledDice = random.nextDouble();
            if (rolledDice < 0.025) {
                set.add(i);
            }

            //Here we add it to the list.
            list.add(i);
        }

        toRemove = set.toArray(new Number[0]);
        Collections.shuffle(list);

        return (T[]) list.toArray(new Integer[0]);
    }

    public static <T extends Number> T[] getBigArray(){
        return (T[]) integers;
    }

    public static <T extends Number> T[] getRemoveArray(){
        return (T[]) toRemove;
    }
}