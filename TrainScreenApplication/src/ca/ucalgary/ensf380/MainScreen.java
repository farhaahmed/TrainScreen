package ca.ucalgary.ensf380;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainScreen extends Application {
    private ScheduledExecutorService scheduler;
    private AdvertisementManager adManager;
    private TrainDataFetcher trainDataFetcher;
    private WeatherFetcher weatherFetcher;
    private NewsFetcher newsFetcher;
    private Canvas mapCanvas;
    private List<StationCoordinate> stationCoordinates;

    // Inner class to hold station coordinates
    private class StationCoordinate {
        private String stationName;
        private double x;
        private double y;

        public StationCoordinate(String stationName, double x, double y) {
            this.stationName = stationName;
            this.x = x;
            this.y = y;
        }

        public String getStationName() {
            return stationName;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }

    @Override
    public void init() {
        adManager = new AdvertisementManager();
        trainDataFetcher = new TrainDataFetcher();
        weatherFetcher = new WeatherFetcher();
        newsFetcher = new NewsFetcher();
        scheduler = Executors.newScheduledThreadPool(1);

        // Initialize station coordinates
        stationCoordinates = new ArrayList<>();
        stationCoordinates.add(new StationCoordinate("Station A", 50, 50));
        stationCoordinates.add(new StationCoordinate("Station B", 150, 150));
        stationCoordinates.add(new StationCoordinate("Station C", 250, 250));
        stationCoordinates.add(new StationCoordinate("Station D", 350, 350));
        // Add more stations as needed
    }

    private double[] getStationCoordinates(String stationName) {
        for (StationCoordinate sc : stationCoordinates) {
            if (sc.getStationName().equals(stationName)) {
                return new double[]{sc.getX(), sc.getY()};
            }
        }
        return new double[]{0, 0}; // Default coordinates if station not found
    }

    @Override
    public void start(Stage primaryStage) {
        mapCanvas = new Canvas(800, 600);
        BorderPane root = new BorderPane();
        root.setCenter(mapCanvas);

        primaryStage.setTitle("Main Screen");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();

        scheduler.scheduleAtFixedRate(() -> displayTrainInfo(), 0, 15, TimeUnit.SECONDS);
    }

    private void displayTrainInfo() {
        List<Train> trains = trainDataFetcher.fetchTrainData("path/to/outputFolder"); // Adjust the path accordingly
        if (!trains.isEmpty()) {
            GraphicsContext gc = mapCanvas.getGraphicsContext2D();
            gc.clearRect(0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());

            for (Train train : trains) {
                double[] coords = getStationCoordinates(train.getCurrentStation());
                gc.fillOval(coords[0], coords[1], 10, 10); // Draw train location as a dot
            }
        }
    }

    @Override
    public void stop() {
        scheduler.shutdown();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
