package com.zimonishim.GUI.resultTables.resultTypes;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * Contains attributes and methods for usage in JavaFX's TableViews. We use this to store results in a spreadsheet-like format.
 */
public class ResultEntry implements Serializable {

    private final String collectionClassName;
    private final String actionName;
    private final int[] beforeActionArray;
    private final long totalTime;
    private final int[] afterActionArray;

    public ResultEntry(Class<?> collectionClass, String actionName, int[] beforeActionArray, Long totalTime, int[] afterActionArray) {
        this(collectionClass.getSimpleName(), actionName, beforeActionArray, totalTime, afterActionArray);
    }

    public ResultEntry(String collectionClassName, String actionName, int[] beforeActionArray, Long totalTime, int[] afterActionArray) {
        this.collectionClassName = collectionClassName;
        this.actionName = actionName;
        this.beforeActionArray = beforeActionArray;
        this.totalTime = totalTime;
        this.afterActionArray = afterActionArray;
    }

    public String getCollectionClassName() {
        return this.collectionClassName;
    }

    public String getActionName() {
        return this.actionName;
    }

    public int[] getBeforeActionArray() {
        return this.beforeActionArray;
    }

    public long getTotalTime() {
        return this.totalTime;
    }

    public int[] getAfterActionArray() {
        return this.afterActionArray;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultEntry that = (ResultEntry) o;
        return totalTime == that.totalTime &&
                Objects.equals(collectionClassName, that.collectionClassName) &&
                Objects.equals(actionName, that.actionName) &&
                Arrays.equals(beforeActionArray, that.beforeActionArray) &&
                Arrays.equals(afterActionArray, that.afterActionArray);
    }
}