package com.zimonishim;

import com.zimonishim.GUI.MainGUI;
import com.zimonishim.tests.TestRunner;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        processStage(stage);
        new MainGUI(stage);
    }

    /**
     * Sets various properties and actions for the stage, such as an icon (?) and a onCloseRequest() to shutdown other threads.
     */
    private static void processStage(Stage stage) {
        stage.setOnCloseRequest(e -> {
            TestRunner.EXECUTOR.shutdown();
            TestRunner.THREAD_POOL_EXECUTOR.shutdown();
        });
    }
}