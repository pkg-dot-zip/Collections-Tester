package com.zimonishim.GUI;

import java.util.Collection;
import java.util.List;

public interface IListSelectionCallback {

    Collection<List> getSelectedLists();
    Collection<Collection> getSelectedSets();
}
