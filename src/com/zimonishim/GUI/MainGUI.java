package com.zimonishim.GUI;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

import static com.zimonishim.tests.SortingTests.sortThread;

public class MainGUI implements IGUI, IGUICallback {

    private final Stage stage;
    private Scene scene;

    private TilePane tilePane = new TilePane();
    private Button testArrayListButton = new Button("Test ArrayList");
    private Button testLinkedListButton = new Button("Test LinkedList");

    private VBox resultVBox = new VBox();

    public MainGUI(Stage stage) {
        this.stage = stage;
        load();
    }

    @Override
    public void load() {
        setup();
        actionHandlingSetup();

        this.scene = new Scene(tilePane);
        this.stage.setScene(scene);
        this.stage.setTitle("Collections Tester");
        this.stage.setMaximized(true);
        this.stage.show();
    }

    @Override
    public void setup() {

        //Adding all the children.


        //Adding it all together.
        this.tilePane.getChildren().addAll(
                testArrayListButton,
                testLinkedListButton,
                resultVBox
        );
    }

    @Override
    public void actionHandlingSetup() {
        testArrayListButton.setOnAction(e -> {
            sortThread(new ArrayList<Integer>(), Comparator.naturalOrder(), this).start();
        });

        testLinkedListButton.setOnAction(e -> {
            sortThread(new LinkedList<Integer>(), Comparator.naturalOrder(), this).start();
        });
    }

    public void addResultToGUI(String result){
        Platform.runLater(() -> {
            resultVBox.getChildren().add(new Label(result));
        });
    }
}