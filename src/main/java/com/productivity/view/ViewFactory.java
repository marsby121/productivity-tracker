package com.productivity.view;

import com.productivity.model.RecordManager;
import com.productivity.controller.BaseController;
import com.productivity.controller.MainWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewFactory {

    private RecordManager recordManager;
    private List<Stage> activeStages;



    public ViewFactory(RecordManager recordManager) {
        this.recordManager = recordManager;
        activeStages = new ArrayList<>();
    }

    public void showMainWindow() {
        BaseController controller = new MainWindow(recordManager, this,
                "/view/MainWindow.fxml");
        initializeStage(controller);
    }

    public void initializeStage(BaseController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource(controller.getFxmlName()));
        fxmlLoader.setController(controller);
        Parent root;

        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        activeStages.add(stage);
        stage.show();
    }

    public void closeStage(Stage stageToClose) {
        stageToClose.close();
        activeStages.remove(stageToClose);
    }
}
