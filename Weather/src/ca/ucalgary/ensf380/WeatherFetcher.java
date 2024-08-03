package ca.ucalgary.ensf380;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WeatherFetcher {

    // Declaring And Initializing The Base URL Used For The Weather Data:
    private static final String BASE_URL = "http://wttr.in/";

    // Public Function/Method Used To Gain Access To The Website API Information Through URL Connection:
    public static String fetchWeather(String cityName, String countryCode) throws Exception {

        // If-Statement To Check For And Implement Default Value For Country Code If That Argument Is Null Or Empty (Edge-Case To Make Sure The URL Works If User Leaves The "countryCode" Empty):
        if (countryCode == null || countryCode.isEmpty()) {
            countryCode = ""; // If The Country Code Is Null Or If There Is No Country Code Then Keep Position Empty.
        } else {
            countryCode = "/" + URLEncoder.encode(countryCode, StandardCharsets.UTF_8.toString()); // If There Is A Country Code Then Add A Forward Slash To Continue Path Of URL Then Add The Country Code To The Right Of The Forward Slash.
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
        if (responseCode == 200) {
            // Using try-with-resources to ensure proper closing of BufferedReader
            try (BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
                String inputLine;
                StringBuilder content = new StringBuilder(); // The Accessed Content Represented By The URL Is Built Into A Java String Object.

                // While Loop Used To Monitor The Access To The Data Represented By The URL And Add Data Values To The "inputLine" Through Content String Appending:
                while ((inputLine = in.readLine()) != null) { // Read Until The End Of The Line Or Page, When Null Has Been Reached That Means Data And Page Has Ended.
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

    // This Is The Method To Parse The Weather Data From The Weather Data Represented By The URL Connection In The HTML Page Using Regex and Matcher:
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

    // Public Function/Method Used To Fetch The Date And Time For A Given City Code:
    public static String fetchDateTime(String cityCode) throws Exception {
        // Constructing the complete URL for the date and time API
        String urlString = "http://worldtimeapi.org/api/timezone/" + URLEncoder.encode(cityCode, StandardCharsets.UTF_8.toString());
        // Creating a URL object from the URL string
        URL url = new URL(urlString);
        // Opening an HTTP connection to the URL
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // Setting the request method to "GET"
        connection.setRequestMethod("GET");

        // Getting the response code from the HTTP request
        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            // Using try-with-resources to ensure proper closing of BufferedReader
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                StringBuilder content = new StringBuilder();

                // Reading the API response and building the content string
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                // Parsing the fetched date and time data and returning it
                String dateTime = content.toString();
                return parseDateTime(dateTime);

            } finally {
                // Disconnecting the URL connection
                connection.disconnect();
            }

        } else {
            // Throwing an exception if the response code is not 200 (OK)
            throw new Exception("Error fetching date and time: " + responseCode);
        }
    }

    // This Is The Method To Parse The Date And Time Data From The JSON Response Using Regex and Matcher:
    private static String parseDateTime(String json) {
        // Regex pattern to match the date and time in the JSON response
        String regex = "\"datetime\":\"(.*?)\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(json);

        if (matcher.find()) {
            // Extracting and returning the date and time using regex group
            return matcher.group(1);
        } else {
            // Returning a default message if no date and time data is found
            return "Date and time not found.";
        }
    }

    public static void main(String[] args) {
        // Checking if the correct number of arguments is provided
        if (args.length != 2) {
            System.out.println("Usage: java WeatherFetcher <cityName> <countryCode>");
            return;
        }

        String cityName = args[0];
        String countryCode = args[1];

        try {
            // Fetching weather and date/time information
            String weather = fetchWeather(cityName, countryCode);
            String dateTime = fetchDateTime(cityName + "/" + countryCode);
            // Printing the fetched information
            System.out.println("Date and Time in " + cityName + ", " + countryCode + ": " + dateTime);
            System.out.println("Weather in " + cityName + ", " + countryCode + ": " + weather);
        } catch (Exception e) {
            // Printing the stack trace in case of an exception
            e.printStackTrace();
        }
    }
}

