package com.zimonishim.GUI;

import com.zimonishim.GUI.resultTables.ResultsTableViewHelper;
import com.zimonishim.GUI.resultTables.resultTypes.ResultEntry;
import com.zimonishim.tests.TestHandler;
import com.zimonishim.tests.TestRunner;
import com.zimonishim.util.SaveHandler;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public class MainGUI implements IGUI, IGUICallback {

    private final Stage stage;
    private Scene scene;

    private final BorderPane topBorderPane = new BorderPane();

    //Main TabPane.
    private final TabPane mainTabPane = new TabPane();
    private final BorderPane borderPane = new BorderPane();
    private final Tab mainTab = new Tab("Main", borderPane);
    private final Button testButton = new Button("Test Collections");

    //Results TabPane.
    private final TabPane resultsTabPane = new TabPane();
    private final Tab resultsTab = new Tab("Results", resultsTabPane);
    private final TableView<ResultEntry> sortTableView = ResultsTableViewHelper.getResultsTableView();
    private final TableView<ResultEntry> addTableView = ResultsTableViewHelper.getResultsTableView();
    private final TableView<ResultEntry> removeTableView = ResultsTableViewHelper.getResultsTableView();
    private final Tab sortTab = new Tab("Sort", sortTableView);
    private final Tab removeTab = new Tab("Remove", removeTableView);
    private final Tab addTab = new Tab("Add", addTableView);
    private final Tab insertTab = new Tab("Insert");

    //Charts TabPane.
    private final Tab chartsTab = new Tab("Charts");

    private TestHandler testHandler = new TestHandler();

    //MenuBar.
    private final MenuBar menuBar = new MenuBar();
    //FileMenu.
    private final Menu fileMenu = new Menu("File");
    private final MenuItem openMenuItem = new Menu("Open");
    private final MenuItem insertMenuItem = new Menu("Insert");
    private final MenuItem saveMenuItem = new Menu("Save");

    private final ProgressIndicator progressIndicator = new ProgressIndicator();

    public MainGUI(Stage stage) {
        this.stage = stage;
        load();
    }

    @Override
    public void load() {
        setup();
        actionHandlingSetup();

        this.scene = new Scene(topBorderPane);
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

        menuBarSetup();

        //Alignment & Spacing.
        this.borderPane.setPadding(new Insets(2));

        //Adding all the children.
        resultsTabPane.getTabs().addAll(
                sortTab,
                removeTab,
                addTab,
                insertTab
        );

        HBox hBox = new HBox(testButton, progressIndicator);
        borderPane.setTop(hBox);

        this.mainTabPane.getTabs().addAll(
                mainTab,
                resultsTab,
                chartsTab
        );

        //Adding it all together.
        topBorderPane.setTop(menuBar);
        topBorderPane.setCenter(mainTabPane);
    }

    private void menuBarSetup() {
        fileMenu.getItems().addAll(openMenuItem, insertMenuItem, new SeparatorMenuItem(), saveMenuItem);
        menuBar.getMenus().add(fileMenu);
    }

    @Override
    public void actionHandlingSetup() {
        menuBarActionHandlingSetup();

        testButton.setOnAction(e -> TestRunner.runAllTestsFromButton(testHandler, this));
    }

    private void menuBarActionHandlingSetup() {
        openMenuItem.setOnAction(e -> refresh(SaveHandler.AsObject.read()));
        insertMenuItem.setOnAction(e -> {
            this.testHandler = SaveHandler.mergeTestHandlers(Arrays.asList(
                    SaveHandler.AsObject.read(),
                    testHandler
            ));

            refresh(this.testHandler);
        });
        saveMenuItem.setOnAction(e -> SaveHandler.AsObject.write(testHandler));
    }

    @Override
    public void addSortResultsToGUI(Collection<ResultEntry> resultEntryCollection) {
        Platform.runLater(() -> sortTableView.getItems().addAll(resultEntryCollection));
    }

    @Override
    public void addAddAllResultsToGUI(Collection<ResultEntry> resultEntryCollection) {
        Platform.runLater(() -> addTableView.getItems().addAll(resultEntryCollection));
    }

    @Override
    public void addRemoveResultsToGUI(Collection<ResultEntry> resultEntryCollection) {
        Platform.runLater(() -> removeTableView.getItems().addAll(resultEntryCollection));
    }

    @Override
    public void reloadCharts(Map<String, Collection<ResultEntry>> results) {
        Platform.runLater(() -> chartsTab.setContent(ChartsHelper.getChartsPane(results)));
    }

    @Override
    public void refresh(TestHandler testHandler) {
        long startTime = System.nanoTime();

        addAddAllResultsToGUI(testHandler.getAddAllResultEntryList());
        addSortResultsToGUI(testHandler.getSortingResultEntryList());
        addRemoveResultsToGUI(testHandler.getRemoveResultEntryList());
        reloadCharts(TestHandler.getResultsMap(testHandler));

        long endTime = System.nanoTime();
        System.out.println("Total time for refreshing: " + (endTime - startTime));
    }

    @Override
    public void setProgress(double progress) {
        Platform.runLater(() -> progressIndicator.setProgress(progress));
    }
}
