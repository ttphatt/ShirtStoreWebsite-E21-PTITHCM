package com.shirtstore.csv;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet("/csvdata")
public class CSVReaderServlet extends HttpServlet {
//    private Map<String, String> messageMap = new HashMap<String, String>();
//    private String csvFilePath = "C:\\Users\\Admin\\Downloads\\message.csv";

//    @Override
//    public void init() throws ServletException {
//        new CSVReaderUtility(csvFilePath);
//        loadCSVData();
//    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        Map<String, String> messageMap = CSVReaderUtility.loadCSVData();
        String id = request.getParameter("id");
        PrintWriter out = response.getWriter();

        if(id != null && messageMap.containsKey(id)){
            out.println("{\"id\": \"" + id + "\", \"message\": \"" + messageMap.get(id) + "\"}");
        }
        else{
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.println("{\"error\": \"Message not found\"}");
        }
    }

//    public Map<String, String> loadCSVData(){
//        try(CSVReader reader = new CSVReader(new FileReader(csvFilePath))){
//            List<String[]> records = reader.readAll();
//            if (!records.isEmpty()) {
//                records.remove(0); // Remove the first row (headers)
//            }
//            for(String[] record : records){
//                if(record.length >= 2){
//                    messageMap.put(record[0], record[1]);
//                }
//            }
//        }
//        catch (IOException | CsvException e){
//            System.out.println("CSV error: " + e.getMessage());
//            e.printStackTrace();
//        }
//        return  messageMap;
//    }
}
