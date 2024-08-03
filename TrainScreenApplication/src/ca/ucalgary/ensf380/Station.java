package ca.ucalgary.ensf380; 

public class Station {
    // Private fields to hold station information
    private int id; // Unique identifier for the station
    private String line; // The line the station belongs to (e.g., Red, Green, Blue)
    private int stationNumber; // The station number on the line
    private String stationCode; // The code representing the station
    private String stationName; // The name of the station
    private double x; // The x-coordinate of the station (for mapping purposes)
    private double y; // The y-coordinate of the station (for mapping purposes)
    private String commonStations; // Common stations with other lines

    // Getter and setter for 'id'
    public int getId() {
        return id; // Returns the station id
    }

    public void setId(int id) {
        this.id = id; // Sets the station id
    }

    // Getter and setter for 'line'
    public String getLine() {
        return line; // Returns the line the station belongs to
    }

    public void setLine(String line) {
        this.line = line; // Sets the line the station belongs to
    }

    // Getter and setter for 'stationNumber'
    public int getStationNumber() {
        return stationNumber; // Returns the station number
    }

    public void setStationNumber(int stationNumber) {
        this.stationNumber = stationNumber; // Sets the station number
    }

    // Getter and setter for 'stationCode'
    public String getStationCode() {
        return stationCode; // Returns the station code
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode; // Sets the station code
    }

    // Getter and setter for 'stationName'
    public String getStationName() {
        return stationName; // Returns the station name
    }

    public void setStationName(String stationName) {
        this.stationName = stationName; // Sets the station name
    }

    // Getter and setter for 'x'
    public double getX() {
        return x; // Returns the x-coordinate of the station
    }

    public void setX(double x) {
        this.x = x; // Sets the x-coordinate of the station
    }

    // Getter and setter for 'y'
    public double getY() {
        return y; // Returns the y-coordinate of the station
    }

    public void setY(double y) {
        this.y = y; // Sets the y-coordinate of the station
    }

    // Getter and setter for 'commonStations'
    public String getCommonStations() {
        return commonStations; // Returns the common stations with other lines
    }

    public void setCommonStations(String commonStations) {
        this.commonStations = commonStations; // Sets the common stations with other lines
    }
}
