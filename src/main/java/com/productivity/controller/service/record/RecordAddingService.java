package com.productivity.controller.service.record;

import com.productivity.model.record.RecordManager;
import com.productivity.model.record.Record;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import static com.productivity.controller.service.record.RecordAddingResult.SUCCESS;

public class RecordAddingService extends Service<RecordAddingResult> {

    private Record record;
    private RecordManager recordManager;

    public RecordAddingService(Record record, RecordManager recordManager) {
        this.record = record;
        this.recordManager = recordManager;
    }


    private RecordAddingResult addRecord() {
        RecordAddingResult result = validateTime();
        if (result != SUCCESS) {
            return result;
        }
        result = validateCategory();
        if (result != SUCCESS) {
            return result;
        }
        recordManager.addRecord(record);
        return SUCCESS;
    }

    @Override
    protected Task<RecordAddingResult> createTask() {
        return new Task<>() {
            @Override
            protected RecordAddingResult call() throws Exception {
                return addRecord();
            }
        };
    }

    private RecordAddingResult validateTime() {
        final int DAY = 60 * 24;
        int min = record.getTime().getMinutes();
        if (min < DAY && min > 0) {
            return SUCCESS;
        }
        return RecordAddingResult.WRONG_TIME_RANGE;
    }

    private RecordAddingResult validateCategory() {
        if (record.getCategory() == null) {
            return RecordAddingResult.NO_CATEGORY;
        }
        return SUCCESS;
    }
}
