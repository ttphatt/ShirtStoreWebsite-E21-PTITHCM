package com.shirtstore.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVReaderUtility {
    private static String csvFilePath = "C:\\Users\\phat\\Downloads\\message.csv";

//    public CSVReaderUtility(String csvFilePath) {
//        this.csvFilePath = csvFilePath;
//    }

    public static Map<String, String> loadCSVData(){
        Map<String, String> messageMap = new HashMap<String, String>();
        try(CSVReader reader = new CSVReader(new FileReader(csvFilePath))){
            List<String[]> records = reader.readAll();
            if (!records.isEmpty()) {
                records.remove(0); // Remove the first row (headers)
            }
            for(String[] record : records){
                if(record.length >= 2){
                    messageMap.put(record[0], record[1]);
                }
            }
        }
        catch (IOException | CsvException e){
            System.out.println("CSV error: " + e.getMessage());
            e.printStackTrace();
        }
        return  messageMap;
    }
}
