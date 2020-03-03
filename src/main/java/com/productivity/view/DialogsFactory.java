package com.productivity.view;

import javafx.scene.control.Alert;

public class DialogsFactory {

    public void showErrorDialog(String problemWith, String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid " + problemWith + "!");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }


}
