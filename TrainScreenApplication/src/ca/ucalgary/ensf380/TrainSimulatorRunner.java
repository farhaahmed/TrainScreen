package ca.ucalgary.ensf380;

import java.io.IOException;
import java.nio.file.*;
import java.util.Timer;
import java.util.TimerTask;

public class TrainSimulatorRunner {

    public static void main(String[] args) {
        String jarPath = "exe/SubwaySimulator.jar"; // Path to the JAR file of the train simulator
        String inputFilePath = "../data/subway.csv"; // Path to the input CSV file containing subway data
        String outputFolderPath = "out"; // Path to the output folder where the simulator will write its files

        // Schedule the task to run the simulator every 15 seconds
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runTrainSimulator(jarPath, inputFilePath, outputFolderPath);
            }
        }, 0, 15000); // Initial delay of 0 ms and repeat every 15 seconds (15000 ms)
    }

    private static void runTrainSimulator(String jarPath, String inputFilePath, String outputFolderPath) {
        // Build the command to run the JAR file with the required arguments
        String[] command = {
            "java", "-jar", jarPath,
            "--in", inputFilePath,
            "--out", outputFolderPath
        };

        try {
            // Execute the command
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.inheritIO(); // To show the output in the console
            Process process = processBuilder.start();

            // Wait for the process to complete
            int exitCode = process.waitFor();
            System.out.println("Train Simulator exited with code: " + exitCode);

            // Additional code to read the generated CSV file and print train positions (optional)
            readAndPrintTrainPositions(outputFolderPath);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void readAndPrintTrainPositions(String outputFolderPath) {
        // Get the most recent CSV file in the output folder
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(outputFolderPath), "Train_*.CSV")) {
            Path latestFile = null;
            for (Path entry : stream) {
                if (latestFile == null || Files.getLastModifiedTime(entry).toMillis() > Files.getLastModifiedTime(latestFile).toMillis()) {
                    latestFile = entry;
                }
            }

            if (latestFile != null) {
                System.out.println("Reading train positions from: " + latestFile);
                // Implement logic to read the CSV file and print train positions
                // This can include parsing the CSV file and printing the contents to the console
                // For simplicity, just print the path of the latest file
                // TODO: Implement actual CSV parsing and train position printing
            } else {
                System.out.println("No train position files found in the output folder.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
