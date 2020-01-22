package com.productivity.model;

import javafx.beans.property.SimpleObjectProperty;

import java.io.Serializable;
import java.time.LocalDate;

//https://stackoverflow.com/questions/41265266/how-to-solve-inaccessibleobjectexception-unable-to-make-member-accessible-m/41265267
// -> problem with gson serialization and LocalDate

public class Record implements Serializable {

    private RecordType type;
    private LocalDate date;
    private String note;
    private int minutes;

    public Record(RecordType type, LocalDate date, String note, int minutes) {
        this.type = type;
        this.date = date;
        this.note = note;
        this.minutes = minutes;
    }

    public String getNote() {
        return note;
    }

    public int getMinutes() {
        return minutes;
    }

    public RecordType getType() {
        return type;
    }

    public LocalDate getDate() {
        return date;
    }


    public void setType(RecordType type) {
        this.type = type;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    @Override
    public String toString() {
        return "Record{" +
                "type=" + type +
                ", time=" + date +
                ", note='" + note + '\'' +
                ", minutes=" + minutes +
                '}';
    }
}
