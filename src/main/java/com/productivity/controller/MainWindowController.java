package com.productivity.controller;

import com.productivity.controller.service.statistics.CategoryStatisticsGenerator;
import com.productivity.model.category.Category;
import com.productivity.model.category.CategoryManager;
import com.productivity.model.record.RecordManager;
import com.productivity.controller.service.record.RecordAddingResult;
import com.productivity.controller.service.record.RecordAddingService;
import com.productivity.model.record.Record;
import com.productivity.model.category.CategoryType;
import com.productivity.model.record.Time;
import com.productivity.view.ViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.net.URL;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class MainWindowController extends BaseController  {


    private static final CategoryType INITIAL_RECORD_TYPE = CategoryType.PRODUCTIVE;

    @FXML
    private ChoiceBox<Category> recordTypeSelect;

    @FXML
    private TextField recordTimeField;

    @FXML
    private TableView<Record> recordTable;

    @FXML
    private TableColumn<Record, LocalDate> dateCol;

    @FXML
    private TableColumn<Record, Category> categoryCol;

    @FXML
    private TableColumn<Record, CategoryType> categoryTypeCol;

    @FXML
    private TableColumn<Record, Time> timeCol;

    @FXML
    private TableColumn<Record, String> noteCol;

    @FXML
    private TextField goalField;

    @FXML
    private TextArea recordNoteArea;

    @FXML
    private Label errorLabel;

    @FXML
    private LineChart<?, ?> timeChart;

    @FXML
    private PieChart categoryChart;

    @FXML
    private Tab timeTab;

    @FXML
    private Tab categoryTab;

    @FXML
    private DatePicker fromDate;

    @FXML
    private DatePicker toDate;



    public MainWindowController(RecordManager recordManager, CategoryManager categoryManager, ViewFactory viewFactory, String fxmlName) {
        super(recordManager, categoryManager, viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpRecordTimeSelect();
        setUpTableView();
        updateCategoryChart();
    }

    private void updateCategoryChart() {
        CategoryStatisticsGenerator statistics = new CategoryStatisticsGenerator(recordManager, categoryManager);
        statistics.start();
        statistics.setOnSucceeded(event -> finalizeCategoryChartUpdate(statistics));
    }

    //decompose to class
    private void finalizeCategoryChartUpdate(CategoryStatisticsGenerator statistics) {
        Map<Category, Double> result = statistics.getValue();
        for (Category category : result.keySet()) {
            PieChart.Data newData = new PieChart.Data(category.getName(), result.get(category));
            updateCategoryChartData(newData);
        }


    }

    private void updateCategoryChartData(PieChart.Data newData) {
        Optional<PieChart.Data> oldData = findCategoryChartDataByName(newData.getName());
        if (oldData.isPresent()) {
            oldData.get().setPieValue(newData.getPieValue());
        } else {
            categoryChart.getData().add(newData);
        }

    }

    private Optional<PieChart.Data> findCategoryChartDataByName(String name) {
       return categoryChart.getData()
                .stream()
                .filter(data -> data.getName().equals(name))
                .findAny();
    }

    private void setUpRecordTimeSelect() {
        ObservableList<Category> categories = categoryManager.getCategories();
        recordTypeSelect.setItems(categories);
        if (!categories.isEmpty()) {
            recordTypeSelect.setValue(categories.get(0));
        }
    }

    private void setUpTableView() {
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        noteCol.setCellValueFactory(new PropertyValueFactory<>("note"));
        categoryTypeCol.setCellValueFactory(new PropertyValueFactory<>("categoryType"));
        recordTable.setItems(recordManager.getRecords());
    }

    @FXML
    void addAction() {
        if ( !fieldsAreValid()) return;
        int minutes = Integer.parseInt(recordTimeField.getText());
        Record record = new Record(recordTypeSelect.getValue(), LocalDate.now(),
                recordNoteArea.getText(), new Time(minutes));
        RecordAddingService service = new RecordAddingService(record, recordManager);
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
                    updateCategoryChart();
                    return;
                case WRONG_TIME_RANGE:
                    errorLabel.setTextFill(Color.RED);
                    errorLabel.setText("1 day max!");
                    return;
                case NO_CATEGORY:
                    errorLabel.setTextFill(Color.RED);
                    errorLabel.setText("No category selected!");
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


    @FXML
    void editTypeAction() {
        viewFactory.showCategorySettings();

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
