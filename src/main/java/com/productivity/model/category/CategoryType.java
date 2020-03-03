package com.productivity.model.category;

public enum CategoryType {
    PRODUCTIVE("Productive"), BREAK("Break"), FREE_TIME("Free time");

    private String name;

    CategoryType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
