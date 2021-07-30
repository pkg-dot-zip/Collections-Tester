package com.zimonishim.GUI.resultTables.resultTypes;

/**
 * Contains attributes and methods for usage in JavaFX's TableViews. We use this to store results in a spreadsheet-like format.
 */
public class ResultEntry {

    protected String collectionClassName;
    protected String actionName;
    protected Number[] beforeActionArray;
    protected Long totalTime;
    protected Number[] afterActionArray;

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