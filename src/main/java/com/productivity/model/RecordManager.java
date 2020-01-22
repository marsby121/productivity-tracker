package com.productivity.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RecordManager {
    private ObservableList<Record> records;
    private final FileRepository fileRepository;

    public RecordManager() {
        records = FXCollections.observableArrayList();
        fileRepository = new FileRepository();
       loadData();
    }
    private void loadData() {
        records = fileRepository.load();
    }

    public void addRecord(Record record) {
        records.add(record);
    }

    public ObservableList<Record> getRecords() {
        return records;
    }

    public void saveData() {
            fileRepository.save(records);
    }
}
