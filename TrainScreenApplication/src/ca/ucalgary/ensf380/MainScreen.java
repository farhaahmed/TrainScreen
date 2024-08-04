package ca.ucalgary.ensf380;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainScreen extends JFrame {
    private ScheduledExecutorService scheduler;
    private AdvertisementManager adManager;
    private TrainDataFetcher trainDataFetcher;
    private WeatherFetcher weatherFetcher;
    private NewsFetcher newsFetcher;

    public MainScreen(AdvertisementManager adManager, TrainDataFetcher trainDataFetcher, WeatherFetcher weatherFetcher, NewsFetcher newsFetcher) {
        this.adManager = adManager;
        this.trainDataFetcher = trainDataFetcher;
        this.weatherFetcher = weatherFetcher;
        this.newsFetcher = newsFetcher;
        scheduler = Executors.newScheduledThreadPool(1);

        setTitle("Main Screen");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel weatherPanel = new JPanel();
        JPanel newsPanel = new JPanel();
        JPanel trainPanel = new JPanel();
        
        add(weatherPanel, BorderLayout.NORTH);
        add(newsPanel, BorderLayout.CENTER);
        add(trainPanel, BorderLayout.SOUTH);

        scheduler.scheduleAtFixedRate(() -> displayWeather(weatherPanel), 0, 1, TimeUnit.HOURS);
        scheduler.scheduleAtFixedRate(() -> displayNews(newsPanel), 0, 15, TimeUnit.MINUTES);
        scheduler.scheduleAtFixedRate(() -> displayTrainInfo(trainPanel), 0, 15, TimeUnit.SECONDS);

        setVisible(true);
    }

    private void displayWeather(JPanel weatherPanel) {
        try {
            String weatherData = weatherFetcher.fetchWeather("Calgary", "CA");
            JLabel weatherLabel = new JLabel(weatherData);
            weatherPanel.removeAll();
            weatherPanel.add(weatherLabel);
            weatherPanel.revalidate();
            weatherPanel.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayNews(JPanel newsPanel) {
        try {
            String newsData = newsFetcher.fetchNews();
            JLabel newsLabel = new JLabel("<html>" + newsData.replaceAll("\n", "<br>") + "</html>");
            newsPanel.removeAll();
            newsPanel.add(newsLabel);
            newsPanel.revalidate();
            newsPanel.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayTrainInfo(JPanel trainPanel) {
        try {
            List<Train> trains = trainDataFetcher.fetchTrainData();
            if (!trains.isEmpty()) {
                Train train = trains.get(0);
                String trainInfo = "Train ID: " + train.getTrainId() + ", Current Station: " + train.getCurrentStation() + ", Speed: " + train.getSpeed() + " km/h";
                JLabel trainLabel = new JLabel(trainInfo);
                trainPanel.removeAll();
                trainPanel.add(trainLabel);
                trainPanel.revalidate();
                trainPanel.repaint();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        AdvertisementManager adManager = new AdvertisementManager();
        TrainDataFetcher trainDataFetcher = new TrainDataFetcher();
        WeatherFetcher weatherFetcher = new WeatherFetcher();
        NewsFetcher newsFetcher = new NewsFetcher();
        new MainScreen(adManager, trainDataFetcher, weatherFetcher, newsFetcher);
    }
}
