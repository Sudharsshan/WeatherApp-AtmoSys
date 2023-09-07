package com.christuniversity.atmosys.fetchData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiDataFetcher {
    // Define global variables for API URLs
    private static final String temperature = "https://io.adafruit.com/api/v2/SudharsshanSY/feeds/temperature";
    private static final String lightIntensity = "https://io.adafruit.com/api/v2/SudharsshanSY/feeds/light-intensity";
    private static final String presssure = "https://io.adafruit.com/api/v2/SudharsshanSY/feeds/pressure";
    private static final String humidity = "https://io.adafruit.com/api/v2/SudharsshanSY/feeds/humidity";
    private static final String rain = "https://io.adafruit.com/api/v2/SudharsshanSY/feeds/rain";
    private static final String altitude = "https://io.adafruit.com/api/v2/SudharsshanSY/feeds/altitude";
    private static final String dewPoint = "https://io.adafruit.com/api/v2/SudharsshanSY/feeds/dew-point";

    // Define global variable for the API key
    private static final String API_KEY = "aio_PfTH79KwfODTYOhQEj2o8lQMWoAa";
    //this method takes the parameter name as input and returns the parameter's last value
    public static String fetchData(String variableName) {
        switch(variableName){
            case "temperature": return fetchLastValue(temperature);
            case "lightIntensity": return fetchLastValue(lightIntensity);
            case "pressure": return fetchLastValue(presssure);
            case "humidity": return fetchLastValue(humidity);
            case "rain": return fetchLastValue(rain);
            case "altitude": return fetchLastValue(altitude);
            case "dewPoint": return fetchLastValue(dewPoint);

            default: return "No value";
        }
    }

    // Method to fetch 'last_value' from the API response
    //this method returns last value of the given parameter.
    public static String fetchLastValue(String apiUrl) { // parameter name
        try {
            // Create a URL object
            URL url = new URL(apiUrl);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method to GET
            connection.setRequestMethod("GET");

            // Set the API key as an HTTP header
            connection.setRequestProperty("X-API-Key", API_KEY);

            // Get the response code
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response data
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                // Close the connection
                connection.disconnect();

                // Parse the JSON data and get the 'last_value' for the specified variable
                // You can use a JSON library like Jackson for more complex parsing
                String jsonData = response.toString();
                int lastIndex = jsonData.indexOf("last_value");         //check this one
                if (lastIndex != -1) {
                    int valueStartIndex = jsonData.indexOf("\"last_value\":", lastIndex);
                    if (valueStartIndex != -1) {
                        int valueEndIndex = jsonData.indexOf(",", valueStartIndex);
                        if (valueEndIndex != -1) {
                            return jsonData.substring(valueStartIndex + 13, valueEndIndex);
                        }
                    }
                }
            } else {
                System.err.println("Error: Failed to fetch data. Response code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Return null if 'last_value' is not found
        return null;
    }
}
