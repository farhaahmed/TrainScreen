package ca.ucalgary.ensf380;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StationManager {
    private List<Station> stations;

    public StationManager() {
        try {
            stations = loadStationsFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Station> loadStationsFromDatabase() throws SQLException {
        List<Station> stationList = new ArrayList<>();
        String query = "SELECT * FROM stations";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Station station = new Station();
                station.setId(resultSet.getInt("id"));
                station.setLine(resultSet.getString("line"));
                station.setStationNumber(resultSet.getInt("station_number"));
                station.setStationCode(resultSet.getString("station_code"));
                station.setStationName(resultSet.getString("station_name"));
                station.setX(resultSet.getDouble("x"));
                station.setY(resultSet.getDouble("y"));
                station.setCommonStations(resultSet.getString("common_stations"));
                stationList.add(station);
            }
        }
        return stationList;
    }

    public List<Station> getStations() {
        return stations;
    }

    public Station getStationByName(String stationName) {
        for (Station station : stations) {
            if (station.getStationName().equals(stationName)) {
                return station;
            }
        }
        return null;
    }

    public void announceNextStation(String currentStationCode) {
        // Logic to announce the next station
        for (Station station : stations) {
            if (station.getStationCode().equals(currentStationCode)) {
                System.out.println("Next stop: " + station.getStationName());
                if (station.getCommonStations() != null && !station.getCommonStations().isEmpty()) {
                    System.out.println("You can change your train to line(s): " + station.getCommonStations());
                }
                break;
            }
        }
    }
}
