package com.shirtstore.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;


public class ReadCSV {
    public static void main(String[] args) {
        String csvPath = "C:\\Users\\phat\\Downloads\\message.csv";
        try(CSVReader reader = new CSVReader(new FileReader(csvPath))){
            List<String[]> records = reader.readAll();
            records.remove(0);
            for(String[] record : records){
                String message_id = record[0];
                String message = record[1];
                System.out.println("Id: " + message_id + ", message: " + message);
            }

        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }
}
