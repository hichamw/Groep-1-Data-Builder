package defaults;
import java.sql.SQLException;

import eventListeners.Login;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Controller extends Application {


	public static void main(String[] args) throws SQLException {
		launch(args);



		
	
		
	}


	@Override
	public void start(Stage stage) throws Exception {
		stage.getIcons().add(new Image("/img/icon.png"));
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/eventListeners/Login.fxml"));
		stage.setScene(new Scene((Pane) loader.load()));
		Login controller = loader.<Login>getController();
		controller.setParent(stage);
		stage.setResizable(false);
		stage.setTitle("Databuilder");
		stage.show();
		
	}
	
	
}
