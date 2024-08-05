package ca.ucalgary.ensf380;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AdvertisementDisplay extends JPanel {
    private AdvertisementManager adManager; // Manager for handling advertisements
    private JLabel adLabel; // Label to display the advertisement
    private int currentAdIndex; // Index of the current advertisement
    private Timer timer; // Timer to schedule advertisement rotation

    // Constructor to initialize the AdvertisementDisplay class
    public AdvertisementDisplay(AdvertisementManager adManager) {
        this.adManager = adManager; // Initialize the AdvertisementManager
        this.currentAdIndex = 0; // Initialize the current advertisement index
        this.timer = new Timer(); // Initialize the timer
        this.adLabel = new JLabel(); // Initialize the label to display ads
        this.adLabel.setHorizontalAlignment(JLabel.CENTER); // Center the label horizontally
        this.adLabel.setVerticalAlignment(JLabel.CENTER); // Center the label vertically
        this.setLayout(new BorderLayout()); // Set the layout to BorderLayout
        this.add(adLabel, BorderLayout.CENTER); // Add the label to the center of the panel
        startAdRotation(); // Start rotating advertisements
    }

    // Method to start rotating advertisements
    private void startAdRotation() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                displayNextAd(); // Display the next advertisement
            }
        }, 0, 10000); // Initial delay of 0 ms and repeat every 10 seconds (10000 ms)
    }

    // Method to display the next advertisement
    private void displayNextAd() {
        List<Advertisement> ads = adManager.getAdvertisements(); // Fetch the list of advertisements
        if (ads.isEmpty()) {
            adLabel.setText("No advertisements available."); // Display message if no ads are available
            return;
        }

        Advertisement ad = ads.get(currentAdIndex); // Get the current advertisement
        adLabel.setText(ad.getAdName()); // Set the ad name as the label text for simplicity

        // Example of handling different ad types (actual implementation will depend on how you handle media)
        switch (ad.getAdType().toLowerCase()) {
            case "pdf":
                adLabel.setText("Displaying PDF: " + ad.getAdName());
                break;
            case "mpg":
                adLabel.setText("Displaying MPG video: " + ad.getAdName());
                break;
            case "jpeg":
            case "bmp":
                // Convert the byte array to an ImageIcon and set it on the label
                ImageIcon imageIcon = new ImageIcon(ad.getAdFile());
                adLabel.setIcon(imageIcon);
                adLabel.setText(null); // Clear the text when displaying an image
                break;
            default:
                adLabel.setText("Unknown ad type: " + ad.getAdName());
                break;
        }

        // Update the current ad index for the next rotation
        currentAdIndex = (currentAdIndex + 1) % ads.size();
    }
}
