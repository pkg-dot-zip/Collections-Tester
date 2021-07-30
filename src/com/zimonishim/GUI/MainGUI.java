package com.zimonishim.GUI;

import com.zimonishim.GUI.resultTables.ResultsTableViewHelper;
import com.zimonishim.GUI.resultTables.resultTypes.ResultEntry;
import com.zimonishim.tests.AddTests;
import com.zimonishim.tests.SortingTests;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

public class MainGUI implements IGUI, IGUICallback {

    private final Stage stage;
    private Scene scene;

    //Main TabPane.
    private TabPane mainTabPane = new TabPane();
    private BorderPane borderPane = new BorderPane();
    private Tab mainTab = new Tab("Main", borderPane);
    private Button testArrayListButton = new Button("Test ArrayList");
    private Button testLinkedListButton = new Button("Test LinkedList");

    //Results TabPane.
    private TabPane resultsTabPane = new TabPane();
    private Tab resultsTab = new Tab("Results", resultsTabPane);
    private TableView<ResultEntry> sortTableView = ResultsTableViewHelper.getResultsTableView();
    private Tab sortTab = new Tab("Sort", sortTableView);
    private Tab removeTab = new Tab("Remove");
    private VBox addResultVBox = new VBox();
    private Tab addTab = new Tab("Add", addResultVBox);
    private Tab insertTab = new Tab("Insert");

    //Charts TabPane.
    private Tab chartsTab = new Tab("Charts");


    public MainGUI(Stage stage) {
        this.stage = stage;
        load();
    }

    @Override
    public void load() {
        setup();
        actionHandlingSetup();

        this.scene = new Scene(mainTabPane);
        this.stage.setScene(scene);
        this.stage.setTitle("Collections Tester");
        this.stage.setMaximized(true);
        this.stage.show();
    }

    @Override
    public void setup() {
        //Value init.
            //TabPanes.
        this.mainTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        this.resultsTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        //Alignment & Spacing.
        this.borderPane.setPadding(new Insets(2));

        //Adding all the children.
        resultsTabPane.getTabs().addAll(
                sortTab,
                removeTab,
                addTab,
                insertTab
        );

        borderPane.setTop(new HBox(testArrayListButton, testLinkedListButton));

        //Adding it all together.
        this.mainTabPane.getTabs().addAll(
                mainTab,
                resultsTab,
                chartsTab
        );
    }

    @Override
    public void actionHandlingSetup() {
        testArrayListButton.setOnAction(e -> {
                SortingTests.sortThread(new ArrayList<Integer>(), Comparator.naturalOrder(), this).start();
                AddTests.addAllThread(new ArrayList<Integer>(), this).start();
        });
        testLinkedListButton.setOnAction(e -> {
                SortingTests.sortThread(new LinkedList<Integer>(), Comparator.naturalOrder(), this).start();
                AddTests.addAllThread(new LinkedList<Integer>(), this).start();
        });
    }

    @Override
    public void addSortResultsToGUI(ResultEntry resultEntry){
        Platform.runLater(() -> {
            sortTableView.getItems().add(resultEntry);
        });
    }

    @Override
    public void addAddAllResultsToGUI(String result) {
        Platform.runLater(() -> {
            addResultVBox.getChildren().add(new Label(result));
        });
    }
}