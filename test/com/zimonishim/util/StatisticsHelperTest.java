package com.zimonishim.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatisticsHelperTest {

    /**
     * Standard Deviation. For more information, visit <a href="https://en.wikipedia.org/wiki/Standard_deviation">Wikipedia</a>.
     * <p>
     * To check the results you can use <a href="https://www.calculator.net/standard-deviation-calculator.html">this online calculator</a>
     * <b>with the mode SET to Sample</b>.
     */
    @Test
    void assertStandardDeviationCaseA() {

        //In this case we test an array of 5, with the total sum of 75. The mean should result in 15.
        double[] doubles = new double[]{5, 10, 15, 20, 25};
        double result = StatisticsHelper.calculateStandardDeviation(doubles);

        assertEquals(7.905694150420948, result, "Standard Deviation does not return expected result.");
    }

    @Test
    void assertStandardDeviationCaseB() {

        //In this case we test an array of 7, with the total sum of 135. The mean should result in 19.285714285714285.
        double[] doubles = new double[]{10, 12, 13, 15, 20, 30, 35};
        double result = StatisticsHelper.calculateStandardDeviation(doubles);

        assertEquals(9.655987533033338, result, "Standard Deviation does not return expected result.");
    }
}