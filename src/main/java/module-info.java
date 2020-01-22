module Productivity.Tracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires gson;
    requires java.sql;

    opens com.productivity;
    opens com.productivity.controller;
    opens com.productivity.model;
}