package com.productivity.model.record;

import com.productivity.model.category.Category;
import com.productivity.model.category.CategoryType;

import java.io.Serializable;
import java.time.LocalDate;

//https://stackoverflow.com/questions/41265266/how-to-solve-inaccessibleobjectexception-unable-to-make-member-accessible-m/41265267
// -> problem with gson serialization and LocalDate

public class Record implements Serializable {

    private Category category;
    private LocalDate date;
    private String note;
    private Time time;

    public Record(Category category, LocalDate date, String note, Time time) {
        this.category = category;
        this.date = date;
        this.note = note;
        this.time = time;
    }

    public String getNote() {
        return note;
    }

    public Time getTime() {
        return time;
    }

    public Category getCategory() {
        return category;
    }

    public LocalDate getDate() {
        return date;
    }


    public void setCategory(Category category) {
        this.category = category;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public CategoryType getCategoryType() {
        return category.getType();
    }


    @Override
    public String toString() {
        return "Record{" +
                "category=" + category +
                ", categoryType=" + category.getType() +
                ", date=" + date +
                ", note='" + note + '\'' +
                ", minutes=" + time +
                '}';
    }
}
