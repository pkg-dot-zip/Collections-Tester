package com.zimonishim.util;

import java.util.*;

/**
 * Contains different collections of data. This allows us to run tests without specifying what data to test.
 */
public class TestData {

    public static final int[] INTEGERS = setupLargeArray(); //Used as default collection content if none is specified.
    public static int[] toRemove; //Used ONLY by the RemoveTest.

    //TODO: Optimize this method by not using collections here. -> Replace collections with arrays.
    private static int[] setupLargeArray() {
        final int dataAmount = 10_000;

        Random random = new Random();

        System.out.println("Setting up large array.");

        List<Integer> list = new ArrayList<>(dataAmount);
        Set<Integer> set = new HashSet<>();

        for (int i = 0; i < dataAmount; i++) {

            //2.5 chance that we add it to the set of random integers.
            double rolledDice = random.nextDouble();
            if (rolledDice < 0.025) {
                set.add(i);
            }

            //Here we add it to the list.
            list.add(i);
        }

        toRemove = set.stream().mapToInt(i -> i).toArray();
        Collections.shuffle(list);

        return list.stream().mapToInt(i -> i).toArray();
    }
}
