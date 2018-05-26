package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Window.fxml"));

			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root, 330, 300);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			GameController controller = fxmlLoader.getController();
			primaryStage.setOnHiding(e -> controller.sendLeaveMessage());
			primaryStage.setTitle("Kółko i Krzyżyk");
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
