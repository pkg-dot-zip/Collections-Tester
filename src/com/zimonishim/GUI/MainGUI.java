package com.zimonishim.GUI;

import com.zimonishim.GUI.resultTables.ResultsTableViewHelper;
import com.zimonishim.GUI.resultTables.resultTypes.ResultEntry;
import com.zimonishim.tests.TestHandler;
import com.zimonishim.tests.TestRunner;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.Collection;
import java.util.Map;

public class MainGUI implements IGUI, IGUICallback {

    private final Stage stage;
    private Scene scene;

    private BorderPane topBorderPane = new BorderPane();

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

    //MenuBar.
    private MenuBar menuBar = new MenuBar();
    private Menu fileMenu = new Menu("File");
    private MenuItem openMenu = new Menu("Open");
    private MenuItem saveMenu = new Menu("Save");


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

        borderPane.setTop(testButton);

        this.mainTabPane.getTabs().addAll(
                mainTab,
                resultsTab,
                chartsTab
        );

        //Adding it all together.
        topBorderPane.setTop(menuBar);
        topBorderPane.setCenter(mainTabPane);
    }

    private void menuBarSetup(){
        fileMenu.getItems().addAll(openMenu, saveMenu);

        menuBar.getMenus().add(fileMenu);
    }

    @Override
    public void actionHandlingSetup() {
        menuBarActionHandlingSetup();

        testButton.setOnAction(e -> {
                new Thread(() -> {
                    TestRunner.runAllTests(testHandler, this);
                }).start();
        });
    }

    private void menuBarActionHandlingSetup() {
        openMenu.setOnAction(e -> {
            try {
                FileInputStream fileInputStream = new FileInputStream("test.dat");
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

                long startTime = System.nanoTime();
                testHandler = (TestHandler) objectInputStream.readObject();
                long endTime = System.nanoTime();
                System.out.println("Total time for reading TestHandler: " + (endTime - startTime));

                System.out.println(testHandler.getAddAllResultEntryList().size());

            } catch (IOException | ClassNotFoundException ioException) {
                ioException.printStackTrace();
            }

            long startTime = System.nanoTime();
            refresh();
            long endTime = System.nanoTime();
            System.out.println("Total time for refreshing: " + (endTime - startTime));
        });

        saveMenu.setOnAction(e -> {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream("test.dat");
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

                long startTime = System.nanoTime();
                objectOutputStream.writeObject(testHandler);
                long endTime = System.nanoTime();
                System.out.println("Total time for writing TestHandler: " + (endTime - startTime));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
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
    public void reloadCharts(Map<String, Collection<ResultEntry>> results){
        Platform.runLater(() -> {
            chartsTab.setContent(ChartsHelper.getChartsPane(results));
        });
    }

    //TODO: Put this in the Callback.
    private void refresh(){
        addAddAllResultsToGUI(testHandler.getAddAllResultEntryList());
        addSortResultsToGUI(testHandler.getSortingResultEntryList());
        addRemoveResultsToGUI(testHandler.getRemoveResultEntryList());
        reloadCharts(TestHandler.getResultsMap(testHandler));
    }
}