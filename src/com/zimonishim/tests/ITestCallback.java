package com.zimonishim.tests;

import com.zimonishim.GUI.resultTables.resultTypes.ResultEntry;

public interface ITestCallback {
    void returnSortResult(ResultEntry resultEntry);
    void returnAddAllResult(ResultEntry resultEntry);
    void returnRemoveResult(ResultEntry resultEntry);
}
