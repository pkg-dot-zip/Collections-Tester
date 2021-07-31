package com.zimonishim.GUI;

import com.zimonishim.GUI.resultTables.ResultsTableViewHelper;
import com.zimonishim.GUI.resultTables.resultTypes.ResultEntry;
import com.zimonishim.tests.TestHandler;
import com.zimonishim.tests.TestRunner;
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

import java.util.Collection;

public class MainGUI implements IGUI, IGUICallback {

    private final Stage stage;
    private Scene scene;

    //Main TabPane.
    private TabPane mainTabPane = new TabPane();
    private BorderPane borderPane = new BorderPane();
    private Tab mainTab = new Tab("Main", borderPane);
    private Button testButton = new Button("Test Collections");

    //Results TabPane.
    private TabPane resultsTabPane = new TabPane();
    private Tab resultsTab = new Tab("Results", resultsTabPane);
    private TableView<ResultEntry> sortTableView = ResultsTableViewHelper.getResultsTableView();
    private TableView<ResultEntry> addTableView = ResultsTableViewHelper.getResultsTableView();
    private TableView<ResultEntry> removeTableView = ResultsTableViewHelper.getResultsTableView();
    private Tab sortTab = new Tab("Sort", sortTableView);
    private Tab removeTab = new Tab("Remove", removeTableView);
    private Tab addTab = new Tab("Add", addTableView);
    private Tab insertTab = new Tab("Insert");

    //Charts TabPane.
    private Tab chartsTab = new Tab("Charts");

    private TestHandler testHandler = new TestHandler();


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

        borderPane.setTop(new HBox(testButton));

        //Adding it all together.
        this.mainTabPane.getTabs().addAll(
                mainTab,
                resultsTab,
                chartsTab
        );
    }

    @Override
    public void actionHandlingSetup() {
        testButton.setOnAction(e -> { //TODO: Check whether this should be run on a separate thread.
                TestRunner.runAllTests(testHandler, this);
        });
    }

    @Override
    public void addSortResultsToGUI(Collection<ResultEntry> resultEntryCollection){
        System.out.println("Starting adding all.");
        Platform.runLater(() -> {
            sortTableView.getItems().addAll(resultEntryCollection);
            System.out.println("Done adding all.");
        });
    }

    @Override
    public void addAddAllResultsToGUI(Collection<ResultEntry> resultEntryCollection) {
        Platform.runLater(() -> {
            addTableView.getItems().addAll(resultEntryCollection);
        });
    }

    @Override
    public void addRemoveResultsToGUI(Collection<ResultEntry> resultEntryCollection) {
        Platform.runLater(() -> {
            removeTableView.getItems().addAll(resultEntryCollection);
        });
    }

    @Override
    public void reloadCharts(Collection<ResultEntry> resultEntryCollection){
        Platform.runLater(() -> {
            chartsTab.setContent(ChartsHelper.getChartsPane(resultEntryCollection));
        });
    }
}