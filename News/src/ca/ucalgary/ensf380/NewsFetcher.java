package ca.ucalgary.ensf380;

import java.io.BufferedReader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NewsFetcher {
    private static final String API_KEY = "a84ec2ca21934fce8029829002e79212"; //Our API key
    private static final String BASE_URL = "https://newsapi.org/v2/top-headlines?country=ca&apiKey=";

    public static String fetchNews(String keyword) throws Exception {
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
                return parseNewsData(content.toString(), keyword);
            } finally {
                urlConnection.disconnect();
            }
        } else {
            throw new Exception("Error! Cannot Fetch News Data: " + responseCode);
        }
    }

    // Method to parse the news data from the JSON response
    private static String parseNewsData(String json, String keyword) throws JSONException {
        JSONObject newsData = new JSONObject(json);
        JSONArray articles = newsData.getJSONArray("articles");
        
        
        StringBuilder headlineContents = new StringBuilder();
        
        for (int i = 0; i < articles.length(); i++) {
        	JSONObject article = articles.getJSONObject(i);
        	String headline = article.getString("title");
        	if (headline.contains(keyword)) {
        		headlineContents.append("	" + headline);
        	}
        }
        
        String allHeadlines = headlineContents.toString();
        return allHeadlines;
    }
}