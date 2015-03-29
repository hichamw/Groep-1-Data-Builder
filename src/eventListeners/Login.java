package eventListeners;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Login implements Initializable {
	Stage parent;
	
	@FXML
	private Button loginButton;
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void logIn(ActionEvent event){
		if(username.getText().equals("lars")){
			
			 System.out.println("noice");
		}
		System.out.println(username.getText());
        try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/eventListeners/Main.fxml"));
    		parent.setScene(new Scene((Pane) loader.load()));
        	parent.centerOnScreen();
    		parent.setTitle("Databuilder");
    		MainWindow controller = loader.<MainWindow>getController();
    		controller.setParent(parent);
    		parent.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
	
	public void setParent(Stage stage){
		
		this.parent = stage;
	}
	
	
	

}
