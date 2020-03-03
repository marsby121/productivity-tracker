package com.productivity.view;

import com.productivity.controller.CategorySettingsController;
import com.productivity.model.category.CategoryManager;
import com.productivity.model.record.RecordManager;
import com.productivity.controller.BaseController;
import com.productivity.controller.MainWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewFactory {

    private RecordManager recordManager;
    private CategoryManager categoryManager;
    private List<Stage> activeStages;



    public ViewFactory(RecordManager recordManager, CategoryManager categoryManager) {
        this.recordManager = recordManager;
        this.categoryManager = categoryManager;
        activeStages = new ArrayList<>();
    }

    public void showMainWindow() {
        BaseController controller = new MainWindowController(recordManager, categoryManager, this,
                "/view/MainWindow.fxml");
        initializeStage(controller);
    }

    public void showCategorySettings(){
        BaseController controller = new CategorySettingsController(recordManager,categoryManager, this,
                "/view/CategorySettings.fxml");
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
