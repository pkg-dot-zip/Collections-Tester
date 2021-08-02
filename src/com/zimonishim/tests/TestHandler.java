package com.zimonishim.tests;

import com.zimonishim.GUI.resultTables.resultTypes.ResultEntry;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class TestHandler implements ITestCallback, Serializable {

    private final Collection<ResultEntry> sortingResultEntryList = new Stack<>();
    private final Collection<ResultEntry> addAllResultEntryList = new Stack<>();
    private final Collection<ResultEntry> removeResultEntryList = new Stack<>();

    public Collection<ResultEntry> getSortingResultEntryList() {
        return this.sortingResultEntryList;
    }

    public Collection<ResultEntry> getAddAllResultEntryList() {
        return this.addAllResultEntryList;
    }

    public Collection<ResultEntry> getRemoveResultEntryList() {
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
    public synchronized void returnRemoveResult(ResultEntry resultEntry) {
        removeResultEntryList.add(resultEntry);
    }
}