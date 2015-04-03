package eventListeners;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Scanner;

import defaults.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Login implements Initializable {
	private Stage parent;
	
	@FXML
	private Button loginButton;
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private Label error;
	@FXML
	private ImageView background;
	
	private Database database;
	private Connection conn = null;
	private boolean loggedin = false;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		background.setImage(new Image("/img/login_background.jpg"));
		database = new Database();
		username.setText("Client");
		password.setText("databuilder");
		
	}

	public void logIn(ActionEvent event){
		String host = "145.24.222.208:8124";
		String DBName = "dataminers";
		String user = username.getText();
		String passwordTemp = password.getText();
		
		String encPassword = URLEncoder.encode(passwordTemp);
		try {
            conn = DriverManager.getConnection("jdbc:mysql://" + host +"/"+ DBName +"?user=" + user + "&password=" + encPassword + "");
            System.out.println("Database connected!");
            loggedin = true;
           
        } catch (SQLException ex) {
            // handle any errors
            //System.out.println("SQLException: " + ex.getMessage());
           //System.out.println("SQLState: " + ex.getSQLState());
            //System.out.println("VendorError: " + ex.getErrorCode());
            error.setText("Wachtwoord en/of gebruikersnaam verkeerd");
        }
		if(loggedin){
			try {
				database.setUser(user, passwordTemp);
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/eventListeners/Main.fxml"));
				parent.setScene(new Scene((Pane) loader.load()));
				parent.centerOnScreen();
				parent.setTitle("Databuilder");
				MainWindow controller = loader.<MainWindow>getController();
				controller.setParent(parent);
				controller.setDatabase(database);
				parent.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	} 
	
	public void setParent(Stage stage){
		
		this.parent = stage;
	}
	
	
	

}
