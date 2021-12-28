package com.zimonishim;

import com.zimonishim.GUI.MainGUI;
import com.zimonishim.tests.TestRunner;
import com.zimonishim.util.TestData;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    //TODO: Look at standard deviation for multi- and singlethreading.
    //TODO: Take a look at Box Plots.

    //TODO: Do less conversions of primitives to objects (and the otherway around) in tests. ->
    // This also means that we should have less streams.

    @Override
    public void start(Stage stage) throws Exception {
        TestData.initLargeArray(); //We first generate the random integer array.
        processStage(stage);
        new MainGUI(stage);
    }

    /**
     * Sets various properties and actions for the stage, such as an icon and a onCloseRequest() to shutdown other threads.
     */
    private static void processStage(Stage stage){
        //TODO: Set window icon here.

        stage.setOnCloseRequest(e -> TestRunner.executor.shutdown());
    }
}