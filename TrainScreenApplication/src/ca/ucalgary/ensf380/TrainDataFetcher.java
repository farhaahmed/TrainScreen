package ca.ucalgary.ensf380;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class TrainDataFetcher {

    public List<Train> fetchTrainData(String outputFolderPath) {
        List<Train> trains = new ArrayList<>();
        Path latestFile = getLatestCSVFile(outputFolderPath);
        if (latestFile == null) {
            System.out.println("No train position files found in the output folder.");
            return trains;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(latestFile.toFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                int trainId = Integer.parseInt(values[0]);
                String lineName = values[1];
                int currentStationIndex = Integer.parseInt(values[2]);
                int speed = Integer.parseInt(values[3]);
                String direction = values[4];
                List<String> stations = new ArrayList<>();
                for (int i = 5; i < values.length; i++) {
                    stations.add(values[i]);
                }
                Train train = new Train(trainId, lineName, stations);
                train.setSpeed(speed);
                train.setDirection(direction);
                train.setCurrentStationIndex(currentStationIndex);
                trains.add(train);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return trains;
    }

    private Path getLatestCSVFile(String outputFolderPath) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(outputFolderPath), "Train_*.CSV")) {
            Path latestFile = null;
            for (Path entry : stream) {
                if (latestFile == null || Files.getLastModifiedTime(entry).toMillis() > Files.getLastModifiedTime(latestFile).toMillis()) {
                    latestFile = entry;
                }
            }
            return latestFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
