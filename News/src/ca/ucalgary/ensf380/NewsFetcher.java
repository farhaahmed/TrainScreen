package ca.ucalgary.ensf380;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NewsFetcher {
    private static final String NEWS_URL = "http://newsapi.org/v2/top-headlines?country=ca&apiKey=YOUR_API_KEY"; // Replace YOUR_API_KEY with an actual API key

    public String fetchNews() throws Exception {
        URL url = new URL(NEWS_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) { // If response is OK
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                StringBuilder content = new StringBuilder();

                // Reading the API response and building the content string
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                // Parsing the fetched news data and returning it
                return parseNewsData(content.toString());

            } finally {
                connection.disconnect();
            }
        } else {
            throw new Exception("Error fetching news: " + responseCode);
        }
    }

    private String parseNewsData(String json) {
        // For simplicity, return the raw JSON data
        // You can implement more complex JSON parsing here if needed
        return json;
    }
}
