package ca.ucalgary.ensf380;
	
import java.io.FileInputStream;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;


public class MainScreenFX extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			AnchorPane base = loader.load(new FileInputStream("C:\\Users\\malik\\OneDrive\\Desktop\\java misc\\TransitScreenProject\\TrainScreenApplication\\src\\ca\\ucalgary\\ensf380\\MainScreen.fxml"));
			MainController controller = (MainController)loader.getController();
			controller.applicationStage = primaryStage;
			
			Scene scene = new Scene(base, 600, 400);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Transit Display");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
