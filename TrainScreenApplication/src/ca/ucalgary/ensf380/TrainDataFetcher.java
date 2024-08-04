package ca.ucalgary.ensf380;

import java.io.BufferedReader; // For reading files
import java.io.FileReader; // For reading files
import java.io.IOException; // For handling input-output exceptions
import java.nio.file.*; // For working with file paths
import java.util.ArrayList; // For using ArrayList
import java.util.List; // For using List

public class TrainDataFetcher {
    private static final String DEFAULT_OUTPUT_FOLDER_PATH = "out"; // Default output folder path

    // Method to fetch the latest train data from the CSV file
    public List<Train> fetchTrainData() {
        return fetchTrainData(DEFAULT_OUTPUT_FOLDER_PATH);
    }

    // Method to fetch the latest train data from the CSV file with specified output folder path
    public List<Train> fetchTrainData(String outputFolderPath) {
        List<Train> trains = new ArrayList<>(); // List to store train objects

        // Get the most recent CSV file in the output folder
        Path latestFile = getLatestCSVFile(outputFolderPath);
        if (latestFile == null) {
            System.out.println("No train position files found in the output folder.");
            return trains; // Return empty list if no files are found
        }

        // Try-with-resources to ensure the BufferedReader is closed properly
        try (BufferedReader br = new BufferedReader(new FileReader(latestFile.toFile()))) {
            String line;
            // Read each line in the CSV file
            while ((line = br.readLine()) != null) {
                String[] values = line.split(","); // Split the line by commas
                int trainId = Integer.parseInt(values[0]); // Parse train ID
                String lineName = values[1]; // Get the line name
                int currentStationIndex = Integer.parseInt(values[2]); // Parse current station index
                int speed = Integer.parseInt(values[3]); // Parse speed
                String direction = values[4]; // Get the direction

                // Create a new Train object and add it to the list
                Train train = new Train(trainId, lineName, new ArrayList<>()); // Assuming the stations list will be set elsewhere
                train.setSpeed(speed); // Set train speed
                train.setDirection(direction); // Set train direction
                trains.add(train); // Add train to the list
            }
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace if an exception occurs
        }

        return trains; // Return the list of trains
    }

    // Helper method to get the most recent CSV file from the output folder
    private Path getLatestCSVFile(String outputFolderPath) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(outputFolderPath), "Train_*.CSV")) {
            Path latestFile = null; // Initialize variable to hold the latest file path
            for (Path entry : stream) {
                // Update latestFile if entry is more recently modified
                if (latestFile == null || Files.getLastModifiedTime(entry).toMillis() > Files.getLastModifiedTime(latestFile).toMillis()) {
                    latestFile = entry;
                }
            }
            return latestFile; // Return the latest file path
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace if an exception occurs
        }
        return null; // Return null if no files are found
    }
}
