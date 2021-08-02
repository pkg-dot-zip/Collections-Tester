package com.zimonishim.tests;

import com.zimonishim.GUI.resultTables.resultTypes.ResultEntry;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class TestHandler implements ITestCallback, Serializable {

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

    public static Map<String, Collection<ResultEntry>> getResultsMap(TestHandler testHandler) {
        Map<String, Collection<ResultEntry>> results = new HashMap<>(5);
        results.put("Add All", testHandler.getAddAllResultEntryList());
        results.put("Sort Natural", testHandler.getSortingResultEntryList());
        results.put("Remove", testHandler.getRemoveResultEntryList());
        return results;
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