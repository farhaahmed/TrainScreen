package ca.ucalgary.ensf380;

import java.util.List; // Import for List data structure
import java.util.ArrayList; // Import for ArrayList data structure

public class Train {
    // Private variables to hold properties of a train
    private int trainId; // Unique identifier for the train
    private String line; // The subway line the train is on (e.g., Red Line, Blue Line)
    private int currentStationIndex; // Index of the current station the train is at
    private int speed; // Speed of the train in km/h
    private String direction; // Direction of the train (e.g., Northbound, Southbound)

    // List to store the stations on the train's line
    private List<String> stations;

    // Constructor to initialize a Train object with its ID, line, and stations
    public Train(int trainId, String line, List<String> stations) {
        this.trainId = trainId; // Set the train ID
        this.line = line; // Set the subway line
        this.stations = new ArrayList<>(stations); // Set the list of stations (make a copy to ensure encapsulation)
        this.currentStationIndex = 0; // Initialize the current station index to the first station
        this.speed = 0; // Initialize the train speed to 0
        this.direction = "Northbound"; // Initialize the train direction (default to Northbound)
    }

    // Getter method for the train ID
    public int getTrainId() {
        return trainId; // Return the train ID
    }

    // Getter method for the line
    public String getLine() {
        return line; // Return the line
    }

    // Getter method for the current station index
    public int getCurrentStationIndex() {
        return currentStationIndex; // Return the current station index
    }

    // Getter method for the speed
    public int getSpeed() {
        return speed; // Return the train speed
    }

    // Setter method for the speed
    public void setSpeed(int speed) {
        this.speed = speed; // Set the train speed
    }

    // Getter method for the direction
    public String getDirection() {
        return direction; // Return the train direction
    }

    // Setter method for the direction
    public void setDirection(String direction) {
        this.direction = direction; // Set the train direction
    }

    // Method to move the train to the next station
    public void moveToNextStation() {
        if (direction.equals("Northbound")) {
            currentStationIndex = (currentStationIndex + 1) % stations.size(); // Move to the next station, wrap around if at the end
        } else {
            currentStationIndex = (currentStationIndex - 1 + stations.size()) % stations.size(); // Move to the previous station, wrap around if at the beginning
        }
    }

    // Method to get the current station name
    public String getCurrentStation() {
        return stations.get(currentStationIndex); // Return the name of the current station
    }

    // Method to print the current status of the train
    public void printStatus() {
        System.out.println("Train ID: " + trainId); // Print the train ID
        System.out.println("Line: " + line); // Print the line
        System.out.println("Current Station: " + getCurrentStation()); // Print the current station name
        System.out.println("Speed: " + speed + " km/h"); // Print the speed
        System.out.println("Direction: " + direction); // Print the direction
        System.out.println(); // Print a newline for better readability
    }

    // Main method to test the Train class
    public static void main(String[] args) {
        // Create a list of stations for the Red Line
        List<String> redLineStations = new ArrayList<>();
        redLineStations.add("Station A");
        redLineStations.add("Station B");
        redLineStations.add("Station C");
        redLineStations.add("Station D");

        // Create a train on the Red Line
        Train train = new Train(1, "Red Line", redLineStations);
        
        // Set the speed and direction of the train
        train.setSpeed(60); // Set speed to 60 km/h
        train.setDirection("Northbound"); // Set direction to Northbound

        // Print the initial status of the train
        train.printStatus();

        // Move the train to the next station and print the status
        train.moveToNextStation();
        train.printStatus();

        // Change direction and move the train to the next station
        train.setDirection("Southbound");
        train.moveToNextStation();
        train.printStatus();
    }
}
