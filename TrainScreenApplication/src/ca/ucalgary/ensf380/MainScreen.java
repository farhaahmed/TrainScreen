package ca.ucalgary.ensf380;

import javax.swing.*;
import java.awt.*;
import java.awt.Canvas;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainScreen {
    private ScheduledExecutorService scheduler;
    private AdvertisementManager adManager;
    private TrainDataFetcher trainDataFetcher;
    private static ArrayList<String> weatherData;
    private static String cityName;
    private static String countryName;
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
    
    public static void main(String[] args) {
    	if (args.length != 0) {
    		cityName = args[0];
    		countryName = args[1];
    	}//TODO close program if no args provided for weather and current train
    	
    	//initializes components from the event dispatch thread
    	EventQueue.invokeLater(() -> {
    		JFrame frame = new JFrame("TransitScreen");
    		frame.setSize(600,400);
    		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		frame.setLayout(new GridBagLayout());
    		frame.setVisible(true);
    		GridBagConstraints gbc = new GridBagConstraints();
    		
    		//ad and map panel
    		JPanel adPanel = new JPanel();
    		JLabel adLabel = new JLabel("");
    		
    		//image setting
    		//TODO set interval for map and ads
    		ImageIcon adImage = new ImageIcon("Advertisements/CG_Ad_3.jpg"); //must initialize icon first
    		Image scaledImage = adImage.getImage().getScaledInstance(460, 200, Image.SCALE_SMOOTH);
    		adImage = new ImageIcon(scaledImage);
    		
    		adLabel.setIcon(adImage);
    		adPanel.add(adLabel);
    		
            
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            gbc.gridheight = 2;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 0.75;
            gbc.weighty = 0.5;
            frame.add(adPanel, gbc);
            
            
            //call fetcher
            try {
				weatherData = WeatherFetcher.fetchWeather(cityName, countryName);
			} catch (Exception e) {
				// catch illegalargexception
				e.printStackTrace();
			}
            //seperate into labels
            JLabel conditions = new JLabel(weatherData.get(0));
            JLabel tempPlus = new JLabel(weatherData.get(1)+"("+weatherData.get(2)+")");
            JLabel windPrecipitation = new JLabel(weatherData.get(3)+" km/h"+weatherData.get(4)+" mm");
            
            JPanel weatherPanel = new JPanel();
            
            
            weatherPanel.setLayout(new BorderLayout());
            JLabel weatherLabel = new JLabel("Today's Weather");
            
            weatherPanel.add(weatherLabel, BorderLayout.PAGE_START);
            weatherPanel.add(conditions, BorderLayout.LINE_START);
            weatherPanel.add(tempPlus, BorderLayout.LINE_END);
            weatherPanel.add(windPrecipitation, BorderLayout.PAGE_END);
            
            gbc.gridx = 2;
            gbc.gridy = 0;
            gbc.gridwidth = 1;
            gbc.gridheight = 2;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 0.25;
            gbc.weighty = 0.5;
            frame.add(weatherPanel, gbc);

            // Train Information
            JPanel trainPanel = new JPanel();
            JLabel trainLabel = new JLabel("Train: Tuscany 1 min, 69 St 1 min");
            trainPanel.add(trainLabel);
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 3;
            gbc.gridheight = 2;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 1.0;
            gbc.weighty = 0.35;
            frame.add(trainPanel, gbc);
            
            //News Section
            JPanel newsPanel = new JPanel();
            JLabel newsLabel = new JLabel("News Section");
            newsPanel.add(newsLabel);
            gbc.gridx = 0;
            gbc.gridy = 5;
            gbc.gridwidth = 3;
            gbc.gridheight = 1;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 1.0;
            gbc.weighty = 0.15;
            frame.add(newsPanel, gbc);
            
           
    	});
    }
    
}