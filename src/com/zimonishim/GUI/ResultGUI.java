package com.zimonishim.GUI;

import com.zimonishim.GUI.resultTables.resultTypes.ResultEntry;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Collection;

/**
 * Contains the GUI elements for the information overview that opens whenever the user clicks the information button in the contextMenu.
 */
public class ResultGUI implements IGUI {

    private final Stage stage = new Stage();
    private final ScrollPane mainPane = new ScrollPane();

    private final Collection<ResultEntry> resultEntries;

    public ResultGUI(Collection<ResultEntry> resultEntries) {
        this.resultEntries = resultEntries;
        load();
    }

    @Override
    public void load() {
        setup();
        actionHandlingSetup();

        Scene scene = new Scene(mainPane);
        this.stage.setScene(scene);
        this.stage.setTitle("Result Information");
        this.stage.setWidth(500);
        this.stage.setHeight(550);
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.show();
    }

    @Override
    public void setup() {
        VBox vBox = new VBox();
        vBox.setSpacing(10);

        resultEntries.forEach(resultEntry -> vBox.getChildren().add(getEntryPane(resultEntry)));
        mainPane.setContent(vBox);
    }

    @Override
    public void actionHandlingSetup() {
    }

    private Pane getEntryPane(ResultEntry resultEntry) {
        VBox vBox = new VBox();

        vBox.setSpacing(2);

        vBox.getChildren().addAll(
                new Label(resultEntry.getCollectionClassName() + " - " + resultEntry.getActionName() + " " + resultEntry.getTotalTime() + " NS"),
                getTabPane(resultEntry),
                new Separator()
        );

        return vBox;
    }

    private TabPane getTabPane(ResultEntry resultEntry) {
        //Init.
        TabPane resultTabPane = new TabPane();
        Tab preActionTab = new Tab("Pre-Action", arrayTextArea(resultEntry.getBeforeActionArray()));
        Tab postActionTab = new Tab("Post-Action", arrayTextArea(resultEntry.getAfterActionArray()));

        //Alignment & Spacing.
        resultTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        //Adding it all together.
        resultTabPane.getTabs().addAll(preActionTab, postActionTab);

        return resultTabPane;
    }

    private TextArea arrayTextArea(int[] array) {
        TextArea textArea = new TextArea();
        textArea.setPadding(new Insets(0, 10, 0, 0));

        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setText(Arrays.toString(array));

        return textArea;
    }
}
