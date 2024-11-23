package com.shirtstore.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NominatimAPI {
    public static double[] getCoordinatesFromAddress(String address) throws Exception {
        String urlString = "https://nominatim.openstreetmap.org/search?q=" + address.replace(" ", "+") + "&format=json";

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "MyAppName/1.0");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONArray jsonResponse = new JSONArray(response.toString());
        if (jsonResponse.length() > 0) {
            JSONObject firstResult = jsonResponse.getJSONObject(0);
            double lat = firstResult.getDouble("lat");
            double lon = firstResult.getDouble("lon");
            return new double[]{lat, lon};
        } else {
            throw new Exception("Không tìm thấy địa chỉ.");
        }
    }
}
