package com.zimonishim.GUI.resultTables.resultTypes;

import java.io.Serializable;

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

    public Long getTotalTime() {
        return this.totalTime;
    }

    public int[] getAfterActionArray() {
        return this.afterActionArray;
    }
}