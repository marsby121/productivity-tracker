package com.productivity;

import com.productivity.model.RecordManager;
import com.productivity.view.ViewFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class Launch extends Application {

   private final RecordManager recordManager;

    public Launch() {
        recordManager = new RecordManager();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        ViewFactory factory = new ViewFactory(recordManager);
        factory.showMainWindow();
    }

    @Override
    public void stop() throws Exception {
        recordManager.saveData();
    }
}
