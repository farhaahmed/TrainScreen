package ca.ucalgary.ensf380;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ca.ucalgary.edu.ensf380.MyApp1;

public class TrainDataFetcher {
	private static final String outputFolderPath = "./out";
	
    public static List<Train> fetchTrainData() {
        List<Train> trains = new ArrayList<>();
        Path latestFile = getLatestCSVFile();
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

    private static Path getLatestCSVFile() {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Path.of(outputFolderPath), "Train_*.CSV")) {
            Path latestFile = null;
            for (Path entry : stream) {
                if (latestFile == null || Files.getLastModifiedTime(entry).toMillis() > Files.getLastModifiedTime(latestFile).toMillis()) {
                    latestFile = entry;
                }
            }
            return latestFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    //testing method
    public static void main(String[] args) {
    	List<Train> trains = TrainDataFetcher.fetchTrainData();
    	
    	MyApp1.startSimulation();
    	Timer timer = new Timer();
    	TimerTask task = new TimerTask() {
    		@Override
    		public void run() {
    			trains.clear();
    			trains.addAll(TrainDataFetcher.fetchTrainData());
    			System.out.println(trains.toArray());
    		}
    	};
    	timer.scheduleAtFixedRate(task, 0, 10000);
    	
    	
    	
    }
    
}
