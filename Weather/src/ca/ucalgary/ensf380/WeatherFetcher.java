package ca.ucalgary.ensf380;

import java.util.regex.Pattern; // Import Pattern for regex
import java.util.ArrayList;
import java.util.regex.Matcher; // Import Matcher for regex
import java.net.URL; // Import URL for handling URL connections
import java.net.HttpURLConnection; // Import HttpURLConnection for handling HTTP connections
import java.io.InputStreamReader; // Import InputStreamReader for reading input streams
import java.io.BufferedReader; // Import BufferedReader for reading text from input streams
import java.net.URLEncoder; // Import URLEncoder for encoding URLs
import java.nio.charset.StandardCharsets; // Import StandardCharsets for character encoding

public class WeatherFetcher {

    // Declaring and initializing the base URL used for fetching weather data
    private static final String BASE_URL = "https://wttr.in/";

    // Public method used to gain access to the website API information through URL connection
    public static ArrayList<String> fetchWeather(String cityName, String countryCode) throws Exception {
        // Constructing the complete URL with encoded city and country information
        String completeUrl = BASE_URL + cityName + "," + countryCode;

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
    private static ArrayList<String> parseWeatherData(String html) {
        // Regex pattern to match the weather data in the HTML response
    	ArrayList<String> weatherData = new ArrayList<>();
        String conditionRegex = "(?<=<\\/span> )[ A-Za-z]+";
        String tempPlusRegex = "(?<=\">)[+\\-0-9]{3}";
        String tempActualRegex = "[0-9]{2}(?=</span> )";
        String windRegex = "[0-9]{2}(?= km)";
        String rainRegex = "[0-9.]{3}(?= mm)";
        String Regex404 = "404 UNKNOWN LOCATION:";
        
        //matchers for each field
        Pattern Pattern404 = Pattern.compile(Regex404);
        Matcher Matcher404 = Pattern404.matcher(html);
        Pattern conditionPattern = Pattern.compile(conditionRegex);
        Matcher conditionMatcher = conditionPattern.matcher(html);
        Pattern tempPlusPattern = Pattern.compile(tempPlusRegex);
        Matcher tempPlusMatcher = tempPlusPattern.matcher(html);
        Pattern tempActualPattern = Pattern.compile(tempActualRegex);
        Matcher tempActualMatcher = tempActualPattern.matcher(html);
        Pattern windPattern = Pattern.compile(windRegex);
        Matcher windMatcher = windPattern.matcher(html);
        Pattern rainPattern = Pattern.compile(rainRegex);
        Matcher rainMatcher = rainPattern.matcher(html);
        
        if (!Matcher404.find() && conditionMatcher.find() && tempPlusMatcher.find() && tempActualMatcher.find() && windMatcher.find() && rainMatcher.find()) {
            // Extracting weather information using regex groups
            String condition = conditionMatcher.group();
            String temperature = tempPlusMatcher.group();
            String temperature_actual = tempActualMatcher.group()
;           String wind = windMatcher.group();
            String precipitation = rainMatcher.group();

            // Formatting and returning the extracted weather information
            weatherData.add(condition);
            weatherData.add(temperature);
            weatherData.add(temperature_actual);
            weatherData.add(wind);
            weatherData.add(precipitation);
            
            return weatherData;
        } else {
        	throw new IllegalArgumentException("No Weather Data found for the given City");
        }
    }
}
