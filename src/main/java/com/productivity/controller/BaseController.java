package com.productivity.controller;

import com.productivity.model.category.CategoryManager;
import com.productivity.model.record.RecordManager;
import com.productivity.view.ViewFactory;
import javafx.fxml.Initializable;

public abstract class BaseController implements Initializable {

    protected RecordManager recordManager;
    protected CategoryManager categoryManager;
    protected ViewFactory viewFactory;
    private String fxmlName;

    public BaseController(RecordManager recordManager,CategoryManager categoryManager, ViewFactory viewFactory, String fxmlName) {
        this.recordManager = recordManager;
        this.categoryManager = categoryManager;
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }

    public String getFxmlName() {
        return fxmlName;
    }
}
