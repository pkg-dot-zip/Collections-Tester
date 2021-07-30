package com.zimonishim.GUI.resultTables.resultTypes;

/**
 * Contains attributes and methods for usage in JavaFX's TableViews. We use this to store results in a spreadsheet-like format.
 */
public class ResultEntry {

    private final String collectionClassName;
    private final String actionName;
    private final Number[] beforeActionArray;
    private final Long totalTime;
    private final Number[] afterActionArray;

    public ResultEntry(Class<?> collectionClass, String actionName, Number[] beforeActionArray, Long totalTime, Number[] afterActionArray) {
        this(collectionClass.getSimpleName(), actionName, beforeActionArray, totalTime, afterActionArray);
    }

    public ResultEntry(String collectionClassName, String actionName, Number[] beforeActionArray, Long totalTime, Number[] afterActionArray) {
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

    public Number[] getBeforeActionArray() {
        return this.beforeActionArray;
    }

    public Long getTotalTime() {
        return this.totalTime;
    }

    public Number[] getAfterActionArray() {
        return this.afterActionArray;
    }
}