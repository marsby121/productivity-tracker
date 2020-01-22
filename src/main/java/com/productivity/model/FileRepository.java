package com.productivity.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class FileRepository {
    private static final String FILE_NAME = "records.bin";


    public void save(ObservableList<Record> records) {
        try {
            List<Record> asArray = new ArrayList<>(records); // bo ObservableList nie jest serializable
            FileOutputStream fos = new FileOutputStream(FILE_NAME);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(asArray);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Record> load() {
        ObservableList<Record> records = FXCollections.observableArrayList();
        try {
            FileInputStream fis = new FileInputStream(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            records.addAll ( (List<Record>) ois.readObject());
            ois.close();
        } catch (FileNotFoundException e) {
            System.out.println("No previous data to load");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return records;
    }
}
