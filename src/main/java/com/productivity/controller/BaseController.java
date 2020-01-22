package com.productivity.controller;

import com.productivity.model.RecordManager;
import com.productivity.view.ViewFactory;

public abstract class BaseController {

    protected RecordManager recordManager;
    protected ViewFactory viewFactory;
    private String fxmlName;

    public BaseController(RecordManager recordManager, ViewFactory viewFactory, String fxmlName) {
        this.recordManager = recordManager;
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }

    public String getFxmlName() {
        return fxmlName;
    }
}
