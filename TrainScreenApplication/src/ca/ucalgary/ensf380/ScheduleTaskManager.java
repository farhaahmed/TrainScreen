package ca.ucalgary.ensf380;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleTaskManager {
    private ScheduledExecutorService scheduler;
    private AdvertisementManager adManager;
    private TrainDataFetcher trainDataFetcher;
    private WeatherFetcher weatherFetcher;
    private NewsFetcher newsFetcher;

    public ScheduleTaskManager(AdvertisementManager adManager, TrainDataFetcher trainDataFetcher, WeatherFetcher weatherFetcher, NewsFetcher newsFetcher) {
        this.adManager = adManager;
        this.trainDataFetcher = trainDataFetcher;
        this.weatherFetcher = weatherFetcher;
        this.newsFetcher = newsFetcher;
        scheduler = Executors.newScheduledThreadPool(1);
    }

    public void start() {
        scheduler.scheduleAtFixedRate(() -> adManager.displayNextAd(), 0, 10, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(() -> {
            try {
                weatherFetcher.fetchWeather("Calgary", "CA");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.HOURS);
        scheduler.scheduleAtFixedRate(() -> {
            try {
                newsFetcher.fetchNews();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 15, TimeUnit.MINUTES);
        scheduler.scheduleAtFixedRate(() -> {
            try {
                trainDataFetcher.fetchTrainData("path/to/outputFolder"); // Adjust the path accordingly
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 15, TimeUnit.SECONDS);
    }

    public void stop() {
        scheduler.shutdown();
    }
}
