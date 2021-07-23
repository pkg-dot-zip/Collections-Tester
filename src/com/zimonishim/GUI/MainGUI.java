package com.zimonishim.GUI;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

import static com.zimonishim.tests.SortingTests.sortThread;

public class MainGUI implements IGUI, IGUICallback {

    private final Stage stage;
    private Scene scene;

    //Main TabPane.
    private TabPane mainTabPane = new TabPane();
    private BorderPane borderPane = new BorderPane();
    private Tab mainTab = new Tab("Main", borderPane);
    private Button testArrayListButton = new Button("Test ArrayList");
    private Button testLinkedListButton = new Button("Test LinkedList");

    //Charts.
    private Tab chartsTab = new Tab("Charts");

    //Results TabPane.
    private TabPane resultsTabPane = new TabPane();
    private VBox resultVBox = new VBox();
    private Tab sortTab = new Tab("Sort", resultVBox);
    private Tab removeTab = new Tab("Remove");
    private Tab addTab = new Tab("Add");
    private Tab insertTab = new Tab("Insert");


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
        borderPane.setCenter(resultsTabPane);

        //Adding it all together.
        this.mainTabPane.getTabs().addAll(
                mainTab,
                chartsTab
        );
    }

    @Override
    public void actionHandlingSetup() {
        testArrayListButton.setOnAction(e -> sortThread(new ArrayList<Integer>(), Comparator.naturalOrder(), this).start());
        testLinkedListButton.setOnAction(e -> sortThread(new LinkedList<Integer>(), Comparator.naturalOrder(), this).start());
    }

    public void addSortResultsToGUI(String result){
        Platform.runLater(() -> {
            resultVBox.getChildren().add(new Label(result));
        });
    }
}