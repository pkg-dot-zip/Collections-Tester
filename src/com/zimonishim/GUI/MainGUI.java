package com.zimonishim.GUI;

import com.zimonishim.GUI.resultTables.ResultsTableViewHelper;
import com.zimonishim.GUI.resultTables.resultTypes.ResultEntry;
import com.zimonishim.tests.AddTests;
import com.zimonishim.tests.SortingTests;
import com.zimonishim.util.CollectionsContainer;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class MainGUI implements IGUI, IGUICallback {

    private final Stage stage;
    private Scene scene;

    //Main TabPane.
    private TabPane mainTabPane = new TabPane();
    private BorderPane borderPane = new BorderPane();
    private Tab mainTab = new Tab("Main", borderPane);
    private Button testListsButton = new Button("Test Lists");
    private Button testSetButton = new Button("Test Set");

    //Results TabPane.
    private TabPane resultsTabPane = new TabPane();
    private Tab resultsTab = new Tab("Results", resultsTabPane);
    private TableView<ResultEntry> sortTableView = ResultsTableViewHelper.getResultsTableView();
    private TableView<ResultEntry> addTableView = ResultsTableViewHelper.getResultsTableView();
    private Tab sortTab = new Tab("Sort", sortTableView);
    private Tab removeTab = new Tab("Remove");
    private Tab addTab = new Tab("Add", addTableView);
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

        borderPane.setTop(new HBox(testListsButton, testSetButton));

        //Adding it all together.
        this.mainTabPane.getTabs().addAll(
                mainTab,
                resultsTab,
                chartsTab
        );
    }

    @Override
    public void actionHandlingSetup() {
        testListsButton.setOnAction(e -> {
            CollectionsContainer.getLists().forEach(l -> {
                List<Integer> list = null;

                try {
                    list = l.getClass().newInstance();
                } catch (InstantiationException | IllegalAccessException instantiationException) {
                    instantiationException.printStackTrace();
                }
                SortingTests.sortThread(list, Comparator.naturalOrder(), this).start();
                AddTests.addAllThread(list, this).start();
            });
        });

        testSetButton.setOnAction(e -> {
            CollectionsContainer.getSets().forEach(s -> {
                Set<Integer> set = null;

                try {
                    set = s.getClass().newInstance();
                } catch (InstantiationException | IllegalAccessException instantiationException) {
                    instantiationException.printStackTrace();
                }

                AddTests.addAllThread(set, this).start();
            });
        });
    }

    @Override
    public void addSortResultsToGUI(ResultEntry resultEntry){
        Platform.runLater(() -> {
            sortTableView.getItems().add(resultEntry);
        });
    }

    @Override
    public void addAddAllResultsToGUI(ResultEntry resultEntry) {
        Platform.runLater(() -> {
            addTableView.getItems().add(resultEntry);
        });
    }
}