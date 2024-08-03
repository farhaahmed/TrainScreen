package ca.ucalgary.ensf380; 

import java.sql.Connection; // Import for managing SQL connections
import java.sql.ResultSet; // Import for handling SQL query results
import java.sql.SQLException; // Import for handling SQL exceptions
import java.sql.Statement; // Import for creating SQL statements
import java.util.ArrayList; // Import for using ArrayList
import java.util.List; // Import for using List

public class StationManager {
    private List<Station> stations; // List to store all stations

    // Constructor initializes StationManager and loads stations from the database
    public StationManager() {
        try {
            stations = loadStationsFromDatabase(); // Load station data from the database
        } catch (SQLException e) {
            e.printStackTrace(); // Print the stack trace if an SQL exception occurs
        }
    }

    // Method to load stations from the database
    private List<Station> loadStationsFromDatabase() throws SQLException {
        List<Station> stationList = new ArrayList<>(); // List to store the stations temporarily
        String query = "SELECT * FROM stations"; // SQL query to select all stations
        Connection connection = null; // Connection object to connect to the database
        Statement statement = null; // Statement object to execute the query
        ResultSet resultSet = null; // ResultSet object to store the query results
        try {
            connection = DatabaseConnection.getConnection(); // Get a connection to the database
            statement = connection.createStatement(); // Create a statement object
            resultSet = statement.executeQuery(query); // Execute the query and store the results in resultSet
            while (resultSet.next()) { // Iterate through the results
                Station station = new Station(); // Create a new Station object
                station.setId(resultSet.getInt("id")); // Set the station id
                station.setLine(resultSet.getString("line")); // Set the line the station belongs to
                station.setStationNumber(resultSet.getInt("station_number")); // Set the station number
                station.setStationCode(resultSet.getString("station_code")); // Set the station code
                station.setStationName(resultSet.getString("station_name")); // Set the station name
                station.setX(resultSet.getDouble("x")); // Set the x-coordinate of the station
                station.setY(resultSet.getDouble("y")); // Set the y-coordinate of the station
                station.setCommonStations(resultSet.getString("common_stations")); // Set the common stations with other lines
                stationList.add(station); // Add the station to the list
            }
        } finally {
            if (resultSet != null && !resultSet.isClosed()) {
                resultSet.close(); // Close the ResultSet
            }
            if (statement != null && !statement.isClosed()) {
                statement.close(); // Close the Statement
            }
            if (connection != null && !connection.isClosed()) {
                connection.close(); // Close the Connection
            }
        }
        return stationList; // Return the list of stations
    }

    // Method to get the next stations for a given train
    public List<Station> getNextStations(String currentStationCode) {
        List<Station> nextStations = new ArrayList<>(); // List to store the next stations
        boolean foundCurrent = false; // Flag to check if the current station is found
        for (Station station : stations) { // Iterate through the stations
            if (foundCurrent) { // If the current station is found
                nextStations.add(station); // Add the station to the next stations list
                if (nextStations.size() == 4) break; // Stop after adding 4 next stations
            }
            if (station.getStationCode().equals(currentStationCode)) {
                foundCurrent = true; // Set the flag to true if the current station is found
            }
        }
        return nextStations; // Return the list of next stations
    }

    // Method to announce the next station and possible line changes
    public void announceNextStation(String currentStationCode) {
        List<Station> nextStations = getNextStations(currentStationCode); // Get the next stations
        if (!nextStations.isEmpty()) { // If there are next stations
            Station nextStation = nextStations.get(0); // Get the next station
            String announcement = "Next stop: " + nextStation.getStationName(); // Prepare the announcement
            if (nextStation.getCommonStations() != null && !nextStation.getCommonStations().isEmpty()) {
                announcement += ", you can change your train to line(s): " + nextStation.getCommonStations(); // Add line change information
            }
            System.out.println(announcement); // Print the announcement
        } else {
            System.out.println("No more stations ahead."); // Print a message if there are no more stations
        }
    }
}
