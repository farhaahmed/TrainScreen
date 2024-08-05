package ca.ucalgary.ensf380;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NewsFetcher {
    private static final String API_KEY = "a84ec2ca21934fce8029829002e79212"; // Your API key
    private static final String BASE_URL = "https://newsapi.org/v2/top-headlines?country=ca&apiKey=";

    public String fetchNews() throws Exception {
        String completeUrl = BASE_URL + API_KEY;
        URL urlObject = new URL(completeUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) urlObject.openConnection();
        urlConnection.setRequestMethod("GET");

        int responseCode = urlConnection.getResponseCode();

        if (responseCode == 200) { // If response is OK
            try (BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
                String inputLine;
                StringBuilder content = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                // Parsing the fetched news data and returning it
                return parseNewsData(content.toString());
            } finally {
                urlConnection.disconnect();
            }
        } else {
            throw new Exception("Error! Cannot Fetch News Data: " + responseCode);
        }
    }

    // Method to parse the news data from the JSON response
    private String parseNewsData(String json) {
        // Implement the JSON parsing logic here
        // For simplicity, we can return the raw JSON or you can use a library like org.json or Gson to parse it
        return json;
    }
}