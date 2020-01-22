package com.productivity.controller;

import com.productivity.model.RecordManager;
import com.productivity.controller.service.RecordAddingResult;
import com.productivity.controller.service.RecordService;
import com.productivity.model.Record;
import com.productivity.model.RecordType;
import com.productivity.view.ViewFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class MainWindow extends BaseController implements Initializable {
    public MainWindow(RecordManager recordManager, ViewFactory viewFactory, String fxmlName) {
        super(recordManager, viewFactory, fxmlName);
    }

    private static final RecordType INITIAL_RECORD_TYPE = RecordType.PRODUCTIVE;

    @FXML
    private ChoiceBox<RecordType> recordTypeSelect;

    @FXML
    private TextField recordTimeField;

    @FXML
    private TableView<Record> recordTable;

    @FXML
    private TableColumn<Record, LocalDate> dateCol;

    @FXML
    private TableColumn<Record, RecordType> typeCol;

    @FXML
    private TableColumn<Record, Integer> timeCol;

    @FXML
    private TableColumn<Record, String> noteCol;

    @FXML
    private LineChart<?, ?> recordMainChart;

    @FXML
    private TextField goalField;

    @FXML
    private TextArea recordNoteArea;

    @FXML
    private Label errorLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpRecordTimeSelect();
        setUpTableView();
    }

    private void setUpTableView() {
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("minutes"));
        noteCol.setCellValueFactory(new PropertyValueFactory<>("note"));
        recordTable.setItems(recordManager.getRecords());
    }

    private void setUpRecordTimeSelect() {
        recordTypeSelect.setItems(FXCollections
                .observableArrayList(RecordType.values()));
        recordTypeSelect.setValue(INITIAL_RECORD_TYPE);
    }

    @FXML
    void addAction() {
        if ( !fieldsAreValid()) return;
        int minutes = Integer.parseInt(recordTimeField.getText());
        Record record = new Record(recordTypeSelect.getValue(), LocalDate.now(),
                recordNoteArea.getText(), minutes);
        RecordService service = new RecordService(record, recordManager);
        service.start();
        service.setOnSucceeded(event -> {
            RecordAddingResult result = service.getValue();
            switch (result) {
                case SUCCESS:
                    errorLabel.setTextFill(Color.GREEN);
                    if (errorLabel.getText().equals("Record added!")) {
                        errorLabel.setText("Next record added!");
                    } else {
                        errorLabel.setText("Record added!");
                    }
                    return;
                case WRONG_TIME_RANGE:
                    errorLabel.setTextFill(Color.RED);
                    errorLabel.setText("1 day max!");
                    return;
            }
        });
            //tworzy obiekt rekord
            //tworzy serwis
            //startuje serwis z tym rekordem-> osobny wątek
    }

    @FXML
    void clearAction() {
        recordNoteArea.setText("");
        recordTimeField.setText("");
        errorLabel.setText("");
    }

    @FXML
    void closeAction() {

    }

    @FXML
    void lockAction() {

    }

    @FXML
    void optionsAction() {
        System.out.println("Test");
    }

    @FXML
    void saveAction() {

    }

    private boolean fieldsAreValid() {
        String minutes = recordTimeField.getText();
        if (minutes.isEmpty()) {
            errorLabel.setTextFill(Color.RED);
            errorLabel.setText("Please fill minutes");
            return false;
        }
        Pattern onlyNumbers = Pattern.compile("\\d+");
        if (!onlyNumbers.matcher(minutes).matches()) {
            errorLabel.setTextFill(Color.RED);
            errorLabel.setText("Only numbers!");
            return false;
        }
        // wybierz typ
        return true;
    }
}
