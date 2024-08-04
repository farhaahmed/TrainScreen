package ca.ucalgary.ensf380;

import java.util.List;
import java.util.ArrayList;

public class Train {
    private int trainId;
    private String line;
    private int currentStationIndex;
    private int speed;
    private String direction;
    private List<String> stations;

    public Train(int trainId, String line, List<String> stations) {
        this.trainId = trainId;
        this.line = line;
        this.stations = new ArrayList<>(stations);
        this.currentStationIndex = 0;
        this.speed = 0;
        this.direction = "Northbound";
    }

    public int getTrainId() {
        return trainId;
    }

    public String getLine() {
        return line;
    }

    public int getCurrentStationIndex() {
        return currentStationIndex;
    }

    public void setCurrentStationIndex(int currentStationIndex) {
        this.currentStationIndex = currentStationIndex;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void moveToNextStation() {
        if (direction.equals("Northbound")) {
            currentStationIndex = (currentStationIndex + 1) % stations.size();
        } else {
            currentStationIndex = (currentStationIndex - 1 + stations.size()) % stations.size();
        }
    }

    public String getCurrentStation() {
        return stations.get(currentStationIndex);
    }

    public void printStatus() {
        System.out.println("Train ID: " + trainId);
        System.out.println("Line: " + line);
        System.out.println("Current Station: " + getCurrentStation());
        System.out.println("Speed: " + speed + " km/h");
        System.out.println("Direction: " + direction);
        System.out.println();
    }

    public static void main(String[] args) {
        List<String> redLineStations = new ArrayList<>();
        redLineStations.add("Station A");
        redLineStations.add("Station B");
        redLineStations.add("Station C");
        redLineStations.add("Station D");

        Train train = new Train(1, "Red Line", redLineStations);
        train.setSpeed(60);
        train.setDirection("Northbound");

        train.printStatus();
        train.moveToNextStation();
        train.printStatus();
        train.setDirection("Southbound");
        train.moveToNextStation();
        train.printStatus();
    }
}
