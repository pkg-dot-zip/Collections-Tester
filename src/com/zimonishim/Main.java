package com.zimonishim;

import com.zimonishim.GUI.MainGUI;
import com.zimonishim.util.TestData;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        new Thread(TestData::initLargeArray).start();
        new MainGUI(stage);
    }
}