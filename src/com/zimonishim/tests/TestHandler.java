package com.zimonishim.tests;

import com.zimonishim.GUI.resultTables.resultTypes.ResultEntry;

import java.io.Serializable;
import java.util.*;

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

    public void addSortingResultCollection(Collection<ResultEntry> collection){
        sortingResultEntryList.addAll(collection);
    }

    public void addAddAllResultCollection(Collection<ResultEntry> collection){
        addAllResultEntryList.addAll(collection);
    }

    public void addRemoveResultCollection(Collection<ResultEntry> collection){
        removeResultEntryList.addAll(collection);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestHandler that = (TestHandler) o;
        return Objects.equals(sortingResultEntryList, that.sortingResultEntryList) &&
                Objects.equals(addAllResultEntryList, that.addAllResultEntryList) &&
                Objects.equals(removeResultEntryList, that.removeResultEntryList);
    }
}