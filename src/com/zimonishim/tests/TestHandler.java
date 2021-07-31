package com.zimonishim.tests;

import com.zimonishim.GUI.resultTables.resultTypes.ResultEntry;

import java.util.Stack;

public class TestHandler implements ITestCallback {

    private final Stack<ResultEntry> sortingResultEntryList = new Stack<>();
    private final Stack<ResultEntry> addAllResultEntryList = new Stack<>();
    private final Stack<ResultEntry> removeResultEntryList = new Stack<>();

    public Stack<ResultEntry> getSortingResultEntryList() {
        return this.sortingResultEntryList;
    }

    public Stack<ResultEntry> getAddAllResultEntryList() {
        return this.addAllResultEntryList;
    }

    public Stack<ResultEntry> getRemoveResultEntryList() {
        return this.removeResultEntryList;
    }

    @Override
    public synchronized void returnSortResult(ResultEntry resultEntry) {
        sortingResultEntryList.add(resultEntry);
    }

    @Override
    public synchronized void returnAddAllResult(ResultEntry resultEntry) {
        addAllResultEntryList.add(resultEntry);
    }

    @Override
    public void returnRemoveResult(ResultEntry resultEntry) {
        removeResultEntryList.add(resultEntry);
    }
}