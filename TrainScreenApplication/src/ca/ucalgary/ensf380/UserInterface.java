package ca.ucalgary.ensf380;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * UserInterface class creates the graphical user interface for the Subway Screen Application.
 * It displays weather information, news, train information, and allows the user to select stations and refresh data.
 */
public class UserInterface extends JFrame {
    // Constants for the dimensions of the window
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    // Components for the GUI
    private StationManager stationManager;
    private WeatherFetcher weatherFetcher;
    private NewsFetcher newsFetcher;
    
    private JLabel weatherLabel;
    private JTextArea newsArea;
    private JLabel trainInfoLabel;
    private JComboBox<String> stationComboBox;
    private JButton refreshButton;
    
    /**
     * Constructor initializes the GUI components and sets up the layout.
     */
    public UserInterface() {
        // Initialize the data managers
        stationManager = new StationManager();
        weatherFetcher = new WeatherFetcher();
        newsFetcher = new NewsFetcher();

        // Set up the frame
        setTitle("Subway Screen Application");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create panels for organizing components
        JPanel topPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel bottomPanel = new JPanel();

        // Top Panel for Weather and Train Info
        topPanel.setLayout(new GridLayout(2, 1));

        // JLabel to display weather information
        weatherLabel = new JLabel("Weather Info", SwingConstants.CENTER);
        topPanel.add(weatherLabel);

        // JLabel to display train information
        trainInfoLabel = new JLabel("Train Information", SwingConstants.CENTER);
        topPanel.add(trainInfoLabel);

        // Center Panel for News
        centerPanel.setLayout(new BorderLayout());

        // JTextArea to display news, inside a JScrollPane for scrolling
        newsArea = new JTextArea();
        newsArea.setEditable(false);
        JScrollPane newsScrollPane = new JScrollPane(newsArea);
        centerPanel.add(newsScrollPane, BorderLayout.CENTER);

        // Bottom Panel for Station Selection and Refresh Button
        bottomPanel.setLayout(new FlowLayout());

        // JComboBox for selecting stations
        stationComboBox = new JComboBox<>();
        loadStations(); // Load stations into the combo box
        bottomPanel.add(stationComboBox);

        // JButton to refresh the data
        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshData(); // Refresh data when button is clicked
            }
        });
        bottomPanel.add(refreshButton);

        // Add panels to the frame
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Initial data load
        refreshData();
    }

    /**
     * Loads station names into the JComboBox.
     */
    private void loadStations() {
        List<Station> stations = stationManager.getStations(); // Get list of stations from StationManager
        for (Station station : stations) {
            stationComboBox.addItem(station.getStationName()); // Add each station name to the combo box
        }
    }

    /**
     * Refreshes weather, news, and train information on the UI.
     */
    private void refreshData() {
        // Update weather info
        String cityName = "Calgary"; // Example city, replace with actual city
        String countryCode = "CA"; // Example country code, replace with actual country code
        try {
            String weather = weatherFetcher.fetchWeather(cityName, countryCode); // Fetch weather data
            weatherLabel.setText("Weather Info: " + weather); // Display weather info
        } catch (Exception e) {
            weatherLabel.setText("Error fetching weather info."); // Handle errors
            e.printStackTrace();
        }

        // Update news info
        try {
            String news = newsFetcher.fetchNews(); // Fetch news data
            newsArea.setText(news); // Display news info
        } catch (Exception e) {
            newsArea.setText("Error fetching news."); // Handle errors
            e.printStackTrace();
        }

        // Update train info
        String selectedStation = (String) stationComboBox.getSelectedItem(); // Get selected station
        if (selectedStation != null) {
            Station station = stationManager.getStationByName(selectedStation); // Get station details
            if (station != null) {
                stationManager.announceNextStation(station.getStationCode()); // Announce next station
                trainInfoLabel.setText("Train Information: Next station is " + station.getStationName()); // Display train info
            }
        }
    }

    /**
     * Main method to start the UserInterface.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserInterface ui = new UserInterface(); // Create and show the UserInterface
            ui.setVisible(true);
        });
    }
}

