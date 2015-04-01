package eventListeners;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.mysql.jdbc.ResultSetMetaData;
import com.sun.prism.paint.Color;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import defaults.Database;
import defaults.Language;
import defaults.LanguageCounter;

public class MainWindow implements Initializable {
	private Stage parent;
	private int limit = 50;
	private Database database;
	private ObservableList<ObservableList> tableData;
	private ObservableList<Data> pieData;
	private int menuIndex = 1;

	@FXML
	private TreeView<String> menuTree;
	@FXML
	private TreeView<String> tableTree;
	@FXML
	private Text tabTitle;
	@FXML
	private ImageView logoutImage;
	@FXML
	private ImageView refreshImage;
	@FXML
	private TextArea recentTweets;
	@FXML
	private TableView dbTable;
	@FXML
	private ContextMenu tableMenu;
	@FXML
	private PieChart languagePie;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		database = new Database();
		logoutImage.setImage(new Image("/img/logout.png"));
		refreshImage.setImage(new Image("/img/refresh.png"));
		fillTreeView();
		createMenuTreeEvent();

	}

	public void retrieveTweets() throws SQLException {
		String query = "SELECT * FROM message ORDER BY Date DESC LIMIT 10";
		ResultSet result = database.retrieveData(query);
		while (result.next()) {
			recentTweets.setText(recentTweets.getText() + "<"
					+ result.getString(1) + "> " + result.getString(3) + ": "
					+ result.getString(2) + "\n");
		}

		database.closeConnection();
		result.close();

	}

	@SuppressWarnings("unchecked")
	public void fillTreeView() {
		menuTree.setStyle("-fx-border-style: none; -fx-background-color:transparent;");
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

		new TreeItem<String>("Edit or"), new TreeItem<String>("view the "),
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
		menuTree.setRoot(dummyRoot);
		menuTree.setShowRoot(false);
		menuTree.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

	}

	@SuppressWarnings("unchecked")
	public void createMenuTreeEvent() {
		menuTree.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener() {
					@Override
					public void changed(ObservableValue arg0, Object arg1,
							Object arg2) {
						int selectedItem = menuTree.getSelectionModel()
								.getSelectedIndex();

						switch (selectedItem) {

						case 0:
						case 1:
						case 2:
						case 3:
							menuTree.getSelectionModel().selectRange(0, 4);
							tabTitle.setText("Table view");
							menuTree.setPrefHeight(640);
							recentTweets.setVisible(true);
							dbTable.setVisible(true);
							tableTree.setVisible(true);
							languagePie.setVisible(false);
							menuIndex = 1;
							break;

						case 4:
						case 5:
						case 6:
						case 7:
							menuTree.getSelectionModel().selectRange(4, 8);
							tabTitle.setText("Language Statistics");
							recentTweets.setVisible(false);
							menuTree.setPrefHeight(800);
							dbTable.setVisible(false);
							tableTree.setVisible(false);
							languagePie.setVisible(true);
							menuIndex = 2;
							break;

						case 8:
						case 9:
						case 10:
						case 11:
							menuTree.getSelectionModel().selectRange(8, 12);
							tabTitle.setText("Tweet Statistics");
							recentTweets.setVisible(false);
							menuTree.setPrefHeight(800);
							dbTable.setVisible(false);
							tableTree.setVisible(false);
							languagePie.setVisible(false);
							menuIndex = 3;
							break;

						case 12:
						case 13:
						case 14:
						case 15:
							menuTree.getSelectionModel().selectRange(12, 16);
							tabTitle.setText("Hashtag Statistics");
							recentTweets.setVisible(false);
							menuTree.setPrefHeight(800);
							dbTable.setVisible(false);
							tableTree.setVisible(false);
							languagePie.setVisible(false);
							menuIndex = 4;
							break;

						}

					}

				});

	}

	public void setParent(Stage stage) {
		this.parent = stage;
	}

	public void refresh() throws SQLException {
		switch(menuIndex){
		case 1:
			retrieveTweets();
			fillTableTree();
			break;
			
		case 2:
			fillLanguagePie();
			break;
			
		case 3:
			
			break;			
		case 4:
		
			break;
		}

		

	}

	public void logOut() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(
					"/eventListeners/Login.fxml"));
			parent.setScene(new Scene((Pane) loader.load()));
			parent.setTitle("Databuilder");
			parent.centerOnScreen();
			parent.show();
			Login controller = loader.<Login> getController();
			controller.setParent(parent);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void fillTableTree() throws SQLException {
		String query = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA='dataminers'";
		ResultSet result = database.retrieveData(query);
		TreeItem<String> root = new TreeItem<String>("Tables:");
		while (result.next()) {
			root.getChildren().add(new TreeItem<String>(result.getString(1)));

		}
		database.closeConnection();
		root.setExpanded(true);
		tableTree.setRoot(root);
		tableTree.setShowRoot(false);
		createTableTreeEvent();

	}

	@SuppressWarnings("unchecked")
	public void createTableTreeEvent() {
		tableTree.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener() {
					@Override
					public void changed(ObservableValue arg0, Object arg1,
							Object arg2) {
						tableData = FXCollections.observableArrayList();
						String selectedItem = tableTree.getSelectionModel().getSelectedItem().getValue();
						String query;
						if (limit == 0) {
							query = "SELECT * FROM " + selectedItem;
						} else {
							query = "SELECT * FROM " + selectedItem + " LIMIT "
									+ limit;
						}

						try {
							ResultSet result = database.retrieveData(query);
							ResultSetMetaData rsmd = (ResultSetMetaData) result
									.getMetaData();

							dbTable.getColumns().clear();

							/*
							 * for(int c = 1; c <= rsmd.getColumnCount(); c++){
							 * dbTable.getColumns().add( new
							 * TableColumn(rsmd.getColumnName(c)) ); }
							 */
							for (int i = 0; i < result.getMetaData()
									.getColumnCount(); i++) {

								// We are using non property style for making
								// dynamic table

								final int j = i;

								TableColumn col = new TableColumn(result
										.getMetaData().getColumnName(i + 1));

								col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {

									public ObservableValue<String> call(
											CellDataFeatures<ObservableList, String> param) {

										return new SimpleStringProperty(param
												.getValue().get(j).toString());

									}

								});

								dbTable.getColumns().addAll(col);


							}

							while (result.next()) {
								ObservableList<String> row = FXCollections
										.observableArrayList();
								for (int i = 1; i <= rsmd.getColumnCount(); i++) {
									row.add(result.getString(i));

								}

								tableData.add(row);
							}
							dbTable.setItems(tableData);

						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				});

	}

	public void fillLanguagePie() throws SQLException{
		LanguageCounter languageCounter = new LanguageCounter();
		languageCounter.extractLanguagesFromDatabase(database);
		ArrayList<Language> languageList = languageCounter.getLanguageList();
		pieData = FXCollections.observableArrayList();
		for (Language langObject : languageList) {
			pieData.add(new PieChart.Data(langObject.getName(), langObject.getCount()));
			
		}
		languagePie.setTitle("Language piechart");
		languagePie.setData(pieData);
				

	
		
		
		
	}

	public void set50Rows() {
		limit = 50;

	}

	public void set100Rows() {
		limit = 100;

	}

	public void setAllRows() {
		limit = 0;

	}

}
