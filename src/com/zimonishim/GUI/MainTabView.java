package com.zimonishim.GUI;

import com.zimonishim.GUI.callbacks.IListSelectionCallback;
import com.zimonishim.GUI.customViews.CollectionCheckBox;
import com.zimonishim.util.CollectionsContainer;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainTabView implements IListSelectionCallback {

    //Main tab.
    private final ListView<CheckBox> listListView = new ListView<>();
    private final ListView<CheckBox> setListView = new ListView<>();
    private final Button testButton = new Button("Test Collections");
    private final ProgressIndicator progressIndicator = new ProgressIndicator();

    public Control getPaneForMainTab() {
        ScrollPane pane = new ScrollPane();
        VBox vBox = new VBox();

        CollectionsContainer.getLists().forEach(list -> listListView.getItems().add(new CollectionCheckBox(list)));
        CollectionsContainer.getSets().forEach(set -> setListView.getItems().add(new CollectionCheckBox(set)));

        vBox.getChildren().add(new HBox(listListView, setListView));
        vBox.getChildren().add(new HBox(testButton, progressIndicator));
        pane.setContent(vBox);
        return pane;
    }

    public Button getTestButton() {
        return this.testButton;
    }

    public ProgressIndicator getProgressIndicator() {
        return this.progressIndicator;
    }

    @Override
    public Collection<List> getSelectedLists() {
        List<List> listToReturn = new ArrayList<>();

        for (CheckBox item : listListView.getItems()) {
            if (!item.isSelected()) continue;
            if (item instanceof CollectionCheckBox) {
                if (((CollectionCheckBox) item).getCollection() instanceof List) {
                    listToReturn.add((List) ((CollectionCheckBox) item).getCollection());
                } else {
                    throw new IllegalStateException("Found not-list-entry in listListView.");
                }
            }
        }

        return listToReturn;
    }

    @Override
    public Collection<Collection> getSelectedSets() {
        List<Collection> listToReturn = new ArrayList<>();

        for (CheckBox item : setListView.getItems()) {
            if (!item.isSelected()) continue;
            if (item instanceof CollectionCheckBox) {
                listToReturn.add(((CollectionCheckBox) item).getCollection());
            }
        }

        return listToReturn;
    }

}
