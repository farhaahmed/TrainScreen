package ca.ucalgary.ensf380;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class TrainDisplay extends JFrame {

    private JTextArea textArea; // Text area to display train information
    private TrainDataFetcher trainDataFetcher; // Instance of TrainDataFetcher to fetch train data
    private String outputFolderPath; // Path to the output folder with train data

    public TrainDisplay(String outputFolderPath) {
        this.outputFolderPath = outputFolderPath; // Initialize the output folder path
        this.trainDataFetcher = new TrainDataFetcher(); // Initialize the train data fetcher

        setTitle("Train Display"); // Set the title of the window
        setSize(600, 400); // Set the size of the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set the default close operation
        setLocationRelativeTo(null); // Center the window

        textArea = new JTextArea(); // Initialize the text area
        textArea.setEditable(false); // Make the text area non-editable
        JScrollPane scrollPane = new JScrollPane(textArea); // Add the text area to a scroll pane
        add(scrollPane); // Add the scroll pane to the frame

        // Schedule the task to update the train information every 15 seconds
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateTrainInformation();
            }
        }, 0, 15000); // Initial delay of 0 ms and repeat every 15 seconds (15000 ms)
    }

    // Method to update train information
    private void updateTrainInformation() {
        List<Train> trains = trainDataFetcher.fetchTrainData(outputFolderPath); // Fetch the latest train data
        StringBuilder trainInfo = new StringBuilder(); // StringBuilder to construct the train information string
        for (Train train : trains) {
            trainInfo.append("Train ID: ").append(train.getTrainId()).append("\n");
            trainInfo.append("Line: ").append(train.getLine()).append("\n");
            trainInfo.append("Current Station: ").append(train.getCurrentStation()).append("\n");
            trainInfo.append("Speed: ").append(train.getSpeed()).append(" km/h\n");
            trainInfo.append("Direction: ").append(train.getDirection()).append("\n\n");
        }
        textArea.setText(trainInfo.toString()); // Update the text area with the train information
    }

    // Main method to create and show the TrainDisplay
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TrainDisplay display = new TrainDisplay("out"); // Initialize the TrainDisplay with the output folder path
                display.setVisible(true); // Make the display visible
            }
        });
    }
}
