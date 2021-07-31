package com.zimonishim.tests;

import com.zimonishim.GUI.resultTables.resultTypes.ResultEntry;

import java.util.Stack;

public class TestHandler implements ITestCallback {

    private final Stack<ResultEntry> sortingResultEntryList = new Stack<>();
    private final Stack<ResultEntry> addAllResultEntryList = new Stack<>();

    public Stack<ResultEntry> getSortingResultEntryList() {
        return this.sortingResultEntryList;
    }

    public Stack<ResultEntry> getAddAllResultEntryList() {
        return this.addAllResultEntryList;
    }

    @Override
    public synchronized void returnSortResult(ResultEntry resultEntry) {
        sortingResultEntryList.add(resultEntry);
    }

    @Override
    public synchronized void returnAddAllResult(ResultEntry resultEntry) {
        addAllResultEntryList.add(resultEntry);
    }
}