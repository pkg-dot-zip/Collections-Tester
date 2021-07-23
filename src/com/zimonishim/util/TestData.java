package com.zimonishim.util;

/**
 * Contains different collections of data. This allows us to run tests without specifying what data to test.
 */
public class TestData {

    public static <T extends Number> T[] getIntegerArray(){
        return (T[]) new Integer[]{
                10, 22, 5, 6,
                3, 3, 21, 22,
                50, 60, 5, 12
        };
    }

}