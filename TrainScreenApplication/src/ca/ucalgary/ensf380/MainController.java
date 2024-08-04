package ca.ucalgary.ensf380;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;


public class MainController {
	Stage applicationStage;
	
	@FXML
	private Label WeatherLabel;
	
	@FXML
	private ImageView imageView;
	
	@FXML
	private MediaView mediaView;
	
	private final Advertisement[] ads = new Advertisement[6];
	private int currentIndex = 0;
	private MediaPlayer mediaPlayer;
	
	@FXML
	public void initialize() {
		//Initialize images and videos
		ads[0] = new Advertisement(1,"KFC","IMAGE","Advertisements/CG_Ad_3.jpg");
		ads[1] = new Advertisement(2, "Madeleines", "IMAGE", "CG_Ad_3.bmp");
		
		//timeline for switching ads
		Timeline timeline = new Timeline(
				new KeyFrame(Duration.seconds(0), event -> switchAd())
			);
		timeline.setCycleCount(Timeline.INDEFINITE);//cycles indefinitely
		timeline.play();
		
	}
	
	private void switchAd() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
		}
		Advertisement currentAd = ads[currentIndex];
		if (currentAd.getAdType().equals("IMAGE")) {
			mediaView.setVisible(false);
			imageView.setImage(new Image(getClass().getResourceAsStream(currentAd.getAdFile())));
			imageView.setVisible(true);
		}
		
		currentIndex = (currentIndex + 1) % ads.length;
	}

}
