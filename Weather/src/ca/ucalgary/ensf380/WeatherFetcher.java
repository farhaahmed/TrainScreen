package ca.ucalgary.ensf380;

import java.util.regex.Pattern; // Import Pattern for regex
import java.util.regex.Matcher; // Import Matcher for regex
import java.net.URL; // Import URL for handling URL connections
import java.net.HttpURLConnection; // Import HttpURLConnection for handling HTTP connections
import java.io.InputStreamReader; // Import InputStreamReader for reading input streams
import java.io.BufferedReader; // Import BufferedReader for reading text from input streams
import java.net.URLEncoder; // Import URLEncoder for encoding URLs
import java.nio.charset.StandardCharsets; // Import StandardCharsets for character encoding

public class WeatherFetcher {

    // Declaring and initializing the base URL used for fetching weather data
    private static final String BASE_URL = "http://wttr.in/";

    // Public method used to gain access to the website API information through URL connection
    public static String fetchWeather(String cityName, String countryCode) throws Exception {

        // If-Statement to check for and implement default value for country code if it is null or empty
        if (countryCode == null || countryCode.isEmpty()) {
            countryCode = ""; // If no country code, keep it empty
        } else {
            countryCode = "/" + URLEncoder.encode(countryCode, StandardCharsets.UTF_8.toString()); // Add the encoded country code to the URL
        }

        // Constructing the complete URL with encoded city and country information
        String completeUrl = BASE_URL + URLEncoder.encode(cityName, StandardCharsets.UTF_8.toString()) + countryCode + "?format=%C+%t+%w+%p";

        // Creating a URL object from the complete URL string
        URL urlObject = new URL(completeUrl);
        // Opening an HTTP connection to the URL
        HttpURLConnection urlConnection = (HttpURLConnection) urlObject.openConnection();
        // Setting the request method to "GET"
        urlConnection.setRequestMethod("GET");

        // Getting the response code from the HTTP request
        int responseCode = urlConnection.getResponseCode();

        // Handling API response code to access the data contents
        if (responseCode == 200) { // If response is OK
            try (BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
                String inputLine;
                StringBuilder content = new StringBuilder(); // The content is built into a String object

                // While loop to read the data represented by the URL and add it to the content string
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                // Parsing the fetched weather data and returning it
                return parseWeatherData(content.toString());

            } finally {
                // Disconnect the URL connection
                urlConnection.disconnect();
            }

        } else {
            // Throwing an exception if the response code is not 200 (OK)
            throw new Exception("Error! Cannot Fetch Weather Data: " + responseCode);
        }
    }

    // Method to parse the weather data from the HTML response using regex and matcher
    private static String parseWeatherData(String html) {
        // Regex pattern to match the weather data in the HTML response
        String regex = "(\\S+\\s+\\S+)\\s+(\\S+)\\s+(\\S+\\s+\\S+)\\s+(\\S+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(html);

        if (matcher.find()) {
            // Extracting weather information using regex groups
            String condition = matcher.group(1);
            String temperature = matcher.group(2);
            String wind = matcher.group(3);
            String precipitation = matcher.group(4);

            // Formatting and returning the extracted weather information
            return String.format("Condition: %s, Temperature: %s, Wind: %s, Precipitation: %s", condition, temperature, wind, precipitation);
        } else {
            // Returning a default message if no weather data is found
            return "Weather data not found.";
        }
    }
}
