package ca.ucalgary.ensf380;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleTaskManager {
    private ScheduledExecutorService scheduler; // Scheduler to manage scheduled tasks
    private AdvertisementManager adManager; // Reference to AdvertisementManager
    private TrainDataFetcher trainDataFetcher; // Reference to TrainDataFetcher
    private WeatherFetcher weatherFetcher; // Reference to WeatherFetcher
    private NewsFetcher newsFetcher; // Reference to NewsFetcher

    // Constructor to initialize ScheduleTaskManager with references to other managers
    public ScheduleTaskManager(AdvertisementManager adManager, TrainDataFetcher trainDataFetcher, 
                               WeatherFetcher weatherFetcher, NewsFetcher newsFetcher) {
        this.adManager = adManager;
        this.trainDataFetcher = trainDataFetcher;
        this.weatherFetcher = weatherFetcher;
        this.newsFetcher = newsFetcher;
        scheduler = Executors.newScheduledThreadPool(1); // Initialize the scheduler
    }

    // Method to start scheduling tasks
    public void start() {
        // Schedule advertisement rotation every 10 seconds
        scheduler.scheduleAtFixedRate(() -> {
            adManager.displayNextAd();
        }, 0, 10, TimeUnit.SECONDS);

        // Schedule weather data fetching every hour
        scheduler.scheduleAtFixedRate(() -> {
            weatherFetcher.fetchWeather("cityCode", "countryCode");
        }, 0, 1, TimeUnit.HOURS);

        // Schedule news data fetching every 15 minutes
        scheduler.scheduleAtFixedRate(() -> {
            newsFetcher.fetchNews("keyword");
        }, 0, 15, TimeUnit.MINUTES);

        // Schedule train data fetching every 15 seconds
        scheduler.scheduleAtFixedRate(() -> {
            trainDataFetcher.fetchTrainData();
        }, 0, 15, TimeUnit.SECONDS);
    }

    // Method to stop scheduling tasks
    public void stop() {
        scheduler.shutdown();
    }
}
