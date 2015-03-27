package eventListeners;


import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import defaults.Database;
import defaults.Language;




public class MainWindow implements Initializable {
	private Stage parent;
	
	@FXML
	private TreeView<String> sideMenu;
	@FXML
	private Text tabTitle;
	@FXML
	private ImageView logoutImage;
	@FXML
	private ImageView refreshImage;
	@FXML
	private TextArea recentTweets;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		logoutImage.setImage(new Image("/img/logout.png"));
		refreshImage.setImage(new Image("/img/refresh.png"));
		recentTweets.setText(String.format("te %n st"));
		fillTreeView();
		createTreeViewEvent();
		
			
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public void fillTreeView(){
		sideMenu.setStyle("-fx-border-style: none; -fx-background-color:transparent;");
		TreeItem<String> dummyRoot = new TreeItem<String>("hidden");
		TreeItem<String> root = new TreeItem<String>("Hashtag Statistics");
		TreeItem<String> root2 = new TreeItem<String>("Tweet Statistics");
		TreeItem<String> root3 = new TreeItem<String>("Language Statistics");
		TreeItem<String> root4 = new TreeItem<String>("Table view");
		
		dummyRoot.getChildren().addAll(root4, root3, root2, root);
		root.setExpanded(true);
		root2.setExpanded(true);
		root3.setExpanded(true);
		root4.setExpanded(true);
		root.getChildren().addAll(
				
				new TreeItem<String>("Edit or"), 
				new TreeItem<String>("view the "), 
				new TreeItem<String>("Database")
				
				);
		root2.getChildren().addAll(
				
				new TreeItem<String>("View statistics"), 
				new TreeItem<String>("about twitter "), 
				new TreeItem<String>("messages.")
				
				);
		root3.getChildren().addAll(
				
				new TreeItem<String>("View statistics"), 
				new TreeItem<String>("about twitter-"), 
				new TreeItem<String>("users' language.")
				
				);
		root4.getChildren().addAll(
				
				new TreeItem<String>("View statistics"), 
				new TreeItem<String>("about most used"), 
				new TreeItem<String>("twitter hashtags")
				
				);
		sideMenu.setRoot(dummyRoot);
		sideMenu.setShowRoot(false);
		sideMenu.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
	}
	
	@SuppressWarnings("unchecked")
	public void createTreeViewEvent(){
		sideMenu.getSelectionModel().selectedItemProperty().addListener( new ChangeListener() {
			@Override
			public void changed(ObservableValue arg0, Object arg1, Object arg2) {
				int selectedItem = sideMenu.getSelectionModel().getSelectedIndex();
				
				switch(selectedItem){
				
				case 0: case 1: case 2: case 3:
					sideMenu.getSelectionModel().selectRange(0, 4);
					tabTitle.setText("Table view");
					sideMenu.setPrefHeight(440);
					recentTweets.setVisible(true);
					break;
					
				case 4: case 5: case 6: case 7:
					sideMenu.getSelectionModel().selectRange(4, 8);
					tabTitle.setText("Language Statistics");
					recentTweets.setVisible(false);
					sideMenu.setPrefHeight(600);
					break;
					
				case 8: case 9: case 10: case 11:
					sideMenu.getSelectionModel().selectRange(8, 12);
					tabTitle.setText("Tweet Statistics");
					recentTweets.setVisible(false);
					sideMenu.setPrefHeight(600);
					break;
					
				case 12: case 13: case 14: case 15:
					sideMenu.getSelectionModel().selectRange(12, 16);
					tabTitle.setText("Hashtag Statistics");
					recentTweets.setVisible(false);
					sideMenu.setPrefHeight(600);
					break;

				}
		        	
			}

	      });
		
	}
	
	public void setParent(Stage stage){
		this.parent = stage;	
	}
	
	public void refresh(){
		System.out.println("test");
		
	}
	
	public void logOut(){
		try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/eventListeners/Login.fxml"));
    		parent.setScene(new Scene((Pane) loader.load()));
    		parent.setTitle("Databuilder");
    		parent.show();
    		Login controller = loader.<Login>getController();
    		controller.setParent(parent);

        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}

	
	
	
	


}
