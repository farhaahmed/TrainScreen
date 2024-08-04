package ca.ucalgary.ensf380;

import javax.swing.*; // Importing Swing components
import java.awt.*; // Importing AWT components
import java.util.List; // Importing List interface

public class MainScreen {
    private JFrame frame; // Main frame for the application
    private AdvertisementManager adManager; // Manager for handling advertisements
    private StationManager stationManager; // Manager for handling station data
    private TrainDataFetcher trainDataFetcher; // Fetcher for real-time train data
    private WeatherFetcher weatherFetcher; // Fetcher for weather data
    private NewsFetcher newsFetcher; // Fetcher for news data
    private ScheduleTaskManager scheduleTaskManager; // Manager for scheduling tasks

    // Constructor to initialize the MainScreen class
    public MainScreen() {
        // Create the main frame
        frame = new JFrame("Subway Screen Application");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Initialize the managers and fetchers
        adManager = new AdvertisementManager();
        stationManager = new StationManager();
        trainDataFetcher = new TrainDataFetcher();
        weatherFetcher = new WeatherFetcher();
        newsFetcher = new NewsFetcher();
        scheduleTaskManager = new ScheduleTaskManager(adManager, trainDataFetcher, weatherFetcher, newsFetcher);

        // Initialize the screen components
        initializeScreen();
        // Start scheduling tasks
        scheduleTaskManager.start();
    }

    // Method to initialize the screen components
    private void initializeScreen() {
        // Create a panel for advertisements
        JPanel adPanel = new JPanel();
        adPanel.setPreferredSize(new Dimension(600, 400));
        adPanel.setBackground(Color.BLACK);
        frame.add(adPanel, BorderLayout.CENTER);

        // Create a panel for weather information
        JPanel weatherPanel = new JPanel();
        weatherPanel.setPreferredSize(new Dimension(200, 100));
        frame.add(weatherPanel, BorderLayout.NORTH);

        // Create a panel for news information
        JPanel newsPanel = new JPanel();
        newsPanel.setPreferredSize(new Dimension(200, 100));
        frame.add(newsPanel, BorderLayout.SOUTH);

        // Create a panel for train information
        JPanel trainPanel = new JPanel();
        trainPanel.setPreferredSize(new Dimension(200, 300));
        frame.add(trainPanel, BorderLayout.EAST);

        // Make the frame visible
        frame.setVisible(true);

        // Start rotating advertisements
        adManager.startAdRotation();

        // Display weather information
        displayWeather(weatherPanel);
        // Display news information
        displayNews(newsPanel);
        // Display train information
        displayTrainInfo(trainPanel, "out"); // Specify the output folder path for the train data
    }

    // Method to display weather information
    private void displayWeather(JPanel weatherPanel) {
        try {
            // Fetch weather data for Calgary, CA
            String weatherData = WeatherFetcher.fetchWeather("Calgary", "CA");
            // Create a label with the weather data
            JLabel weatherLabel = new JLabel(weatherData);
            // Add the weather label to the weather panel
            weatherPanel.add(weatherLabel);
        } catch (Exception e) {
            // Print stack trace if an exception occurs
            e.printStackTrace();
        }
    }

    // Method to display news information
    private void displayNews(JPanel newsPanel) {
        // Fetch news data with a keyword filter
        String newsData = newsFetcher.fetchNews("keyword");
        // Create a label with the news data
        JLabel newsLabel = new JLabel("<html>" + newsData.replace("\n", "<br>") + "</html>");
        // Add the news label to the news panel
        newsPanel.add(newsLabel);
    }

    // Method to display train information
    private void displayTrainInfo(JPanel trainPanel, String outputFolderPath) {
        // Fetch the train data
        List<Train> trains = trainDataFetcher.fetchTrainData(outputFolderPath);
        // Assuming we display the first train's information for simplicity
        if (!trains.isEmpty()) {
            Train train = trains.get(0); // Get the first train from the list
            // Create a label with the train information
            JLabel trainLabel = new JLabel("Train ID: " + train.getTrainId() + ", Line: " + train.getLine() +
                    ", Current Station: " + train.getCurrentStation() + ", Direction: " + train.getDirection() +
                    ", Speed: " + train.getSpeed() + " km/h");
            // Add the train label to the train panel
            trainPanel.add(trainLabel);
        }
    }

    // Main method to run the application
    public static void main(String[] args) {
        new MainScreen(); // Create an instance of MainScreen
    }
}
