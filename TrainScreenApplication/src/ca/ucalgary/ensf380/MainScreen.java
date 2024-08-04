package ca.ucalgary.ensf380;

import javax.swing.*;
import java.awt.*;

public class MainScreen {
    private JFrame frame;
    private AdvertisementManager adManager;
    private StationManager stationManager;
    private TrainDataFetcher trainDataFetcher;
    private WeatherFetcher weatherFetcher;
    private NewsFetcher newsFetcher;
    private ScheduleTaskManager scheduleTaskManager;

    public MainScreen() {
        frame = new JFrame("Subway Screen Application");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        adManager = new AdvertisementManager();
        stationManager = new StationManager();
        trainDataFetcher = new TrainDataFetcher();
        weatherFetcher = new WeatherFetcher();
        newsFetcher = new NewsFetcher();
        scheduleTaskManager = new ScheduleTaskManager(adManager, trainDataFetcher, weatherFetcher, newsFetcher);

        initializeScreen();
        scheduleTaskManager.start(); // Start scheduling tasks
    }

    private void initializeScreen() {
        // Panel for advertisements
        JPanel adPanel = new JPanel();
        adPanel.setPreferredSize(new Dimension(600, 400));
        adPanel.setBackground(Color.BLACK);
        frame.add(adPanel, BorderLayout.CENTER);

        // Panel for weather
        JPanel weatherPanel = new JPanel();
        weatherPanel.setPreferredSize(new Dimension(200, 100));
        frame.add(weatherPanel, BorderLayout.NORTH);

        // Panel for news
        JPanel newsPanel = new JPanel();
        newsPanel.setPreferredSize(new Dimension(200, 100));
        frame.add(newsPanel, BorderLayout.SOUTH);

        // Panel for train information
        JPanel trainPanel = new JPanel();
        trainPanel.setPreferredSize(new Dimension(200, 300));
        frame.add(trainPanel, BorderLayout.EAST);

        frame.setVisible(true);

        // Start advertisement rotation
        adManager.startAdRotation();

        // Display weather and news
        displayWeather(weatherPanel);
        displayNews(newsPanel);

        // Display train information
        displayTrainInfo(trainPanel);
    }

    private void displayWeather(JPanel weatherPanel) {
        String weatherData = weatherFetcher.fetchWeatherData("cityCode");
        JLabel weatherLabel = new JLabel(weatherData);
        weatherPanel.add(weatherLabel);
    }

    private void displayNews(JPanel newsPanel) {
        String newsData = newsFetcher.fetchNews("keyword");
        JLabel newsLabel = new JLabel("<html>" + newsData.replace("\n", "<br>") + "</html>");
        newsPanel.add(newsLabel);
    }

    private void displayTrainInfo(JPanel trainPanel) {
        List<Train> trains = trainDataFetcher.fetchTrainData();
        // Assuming you display the first train's information for simplicity
        if (!trains.isEmpty()) {
            Train train = trains.get(0);
            JLabel trainLabel = new JLabel("Train ID: " + train.getId() + ", Line: " + train.getLine() +
                    ", Current Station: " + train.getCurrentStation() + ", Direction: " + train.getDirection());
            trainPanel.add(trainLabel);
        }
    }

    public static void main(String[] args) {
        new MainScreen();
    }
}
