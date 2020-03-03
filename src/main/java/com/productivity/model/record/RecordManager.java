package com.productivity.model.record;

import com.productivity.model.FileRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class RecordManager {
    private ObservableList<Record> records;
    private final FileRepository fileRepository;

    public RecordManager() {
        records = FXCollections.observableArrayList();
        fileRepository = new FileRepository("records.bin");
        loadAllData();
    }

    private void loadAllData() {
        records.addAll(fileRepository.load());
    }

    //add should save! remove too
    public void addRecord(Record record) {
        records.add(record);
    }

    public ObservableList<Record> getRecords() {
        return records;
    }

    public void saveData() {
        fileRepository.save(records);
    }

    public void filterByDate(LocalDate from, LocalDate to) {
        List<Record> allByDate = getAllByDate(from, to);
        records.clear();
        records.addAll(allByDate);
    }

    private List<Record> getAllByDate(LocalDate from, LocalDate to) {
        List<Record> loaded = fileRepository.load();
       return loaded.stream()
                .filter(record -> !record.getDate().isBefore(from) && !record.getDate().isAfter(to))
                .collect(Collectors.toList());
    }


}
