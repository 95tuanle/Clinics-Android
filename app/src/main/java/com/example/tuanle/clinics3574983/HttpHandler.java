package com.example.tuanle.clinics3574983;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHandler {
    public static String status;

    public static String getRequest(String urlString) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line  = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static String postRequest(String urlString, Clinic clinic) {
        try {
            // prep the connection
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            // prep the json obj
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", clinic.name);
            jsonObject.put("address", clinic.address);
            jsonObject.put("rating", clinic.rating);
            jsonObject.put("latitute", clinic.latitude);
            jsonObject.put("longitute", clinic.longitude);
            jsonObject.put("impression", clinic.impression);
            jsonObject.put("lead_physician", clinic.leadPhysician);
            jsonObject.put("specialization", clinic.specialization);
            jsonObject.put("average_price", clinic.averagePrice);
            // write to db
            DataOutputStream os = new DataOutputStream(connection.getOutputStream());
            os.writeBytes(jsonObject.toString());
            os.flush();
            os.close();
            status = connection.getResponseCode() + ": " + connection.getResponseMessage();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return status;
    }
}
