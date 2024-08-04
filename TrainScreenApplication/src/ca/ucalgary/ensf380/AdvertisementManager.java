package ca.ucalgary.ensf380;

import java.sql.SQLException; // Import for handling SQL exceptions
import java.util.List; // Import for List data structure
import java.util.Timer; // Import for Timer to schedule tasks
import java.util.TimerTask; // Import for TimerTask to define tasks

public class AdvertisementManager {
    private List<Advertisement> ads; // List to store advertisements fetched from the database
    private int currentAdIndex; // Index to keep track of the current advertisement being displayed
    private Timer timer; // Timer to schedule ad rotation tasks

    // Constructor to initialize the AdvertisementManager
    public AdvertisementManager() {
        try {
            ads = DatabaseConnection.fetchAdvertisements(); // Fetch advertisements from the database and store them in the list
        } catch (SQLException e) {
            e.printStackTrace(); // Print the stack trace if there's an SQL exception
        }
        currentAdIndex = 0; // Initialize the current advertisement index to 0
        timer = new Timer(); // Initialize the timer
    }

    // Method to start rotating advertisements
    public void startAdRotation() {
        // Schedule a task to rotate advertisements every 10 seconds
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                displayNextAd(); // Call method to display the next advertisement
            }
        }, 0, 10000); // Schedule task with initial delay of 0 ms and repeat every 10,000 ms (10 seconds)
    }

    // Method to display the next advertisement
    void displayNextAd() {
        Advertisement ad = ads.get(currentAdIndex); // Get the current advertisement based on the current index
        // Implement logic to display the advertisement (e.g., update UI component)
        currentAdIndex = (currentAdIndex + 1) % ads.size(); // Increment the index and wrap around if it exceeds the list size
    }

    // Method to display the map periodically
    public void displayMap() {
        // Schedule a task to display the map every 10 seconds for 5 seconds
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Logic to display the map (e.g., update UI component)
            }
        }, 0, 10000); // Schedule task with initial delay of 0 ms and repeat every 10,000 ms (10 seconds)
    }

	public List<Advertisement> getAdvertisement() {
		// TODO Auto-generated method stub
		return null;
	}
}
