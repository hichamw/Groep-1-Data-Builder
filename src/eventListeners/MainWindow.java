package eventListeners;

import java.awt.event.ActionListener;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;
import defaults.Database;
import defaults.Day;
import defaults.DayCounter;
import defaults.Hashtag;
import defaults.HashtagCounter;
import defaults.Language;
import defaults.LanguageCounter;
import defaults.Time;
import defaults.TweetCounter;

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
	private ImageView logoutImage,refreshImage;
	@FXML
	private TextArea recentTweets;
	@FXML
	private TextField graph2Start, graph2End;
	@FXML
	private TableView dbTable;
	@FXML
	private ContextMenu tableMenu;
	@FXML
	private PieChart pieChart;
	@FXML
	private LineChart<Number, Number> graphChart;
	@FXML
	private LineChart<String, Number> graphChart2;
	@FXML
	private NumberAxis graphX,graphY,graph2Y;
	@FXML
	private CategoryAxis graph2X;
	@FXML
	private WebView googleMap;
	@FXML
	private Label locationLabel, startLabel, endLabel;
	@FXML
	private ChoiceBox<String> locationChoice, pieChoice;
	@FXML
	private RadioButton radioHour, radioDay;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		logoutImage.setImage(new Image("/img/logout.png"));
		refreshImage.setImage(new Image("/img/refresh.png"));
		fillTreeView();
		createMenuTreeEvent();
		ObservableList<String> zoomLevels = FXCollections.observableArrayList("9","8","7","6", "5", "4", "3", "2", "1");
		locationChoice.setItems(zoomLevels);
		locationChoice.valueProperty().set("8");
		locationChoice.valueProperty().addListener(new ChangeListener<String>() {
	        @Override public void changed(ObservableValue ov, String t, String t1) {
	            try {
					fillGoogleMap();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	          }    
	      });
		
		ObservableList<String> pieSelection = FXCollections.observableArrayList("view Hashtag Piechart", "view Language Piechart");
		pieChoice.setItems(pieSelection);
		pieChoice.valueProperty().set("view Hashtag Piechart");
		pieChoice.valueProperty().addListener(new ChangeListener<String>() {
	        @Override public void changed(ObservableValue ov, String t, String t1) {
	        	try {
					fillPie();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	          }    
	      });

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
		TreeItem<String> root = new TreeItem<String>("Tweet locations");
		TreeItem<String> root2 = new TreeItem<String>("Tweet Statistics");
		TreeItem<String> root3 = new TreeItem<String>("Piecharts");
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
				new TreeItem<String>("language's and"),
				new TreeItem<String>("hashtags")

		);
		root4.getChildren().addAll(

		new TreeItem<String>("View the locations"),
				new TreeItem<String>("of tweets on"),
				new TreeItem<String>("Google maps")

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
							pieChart.setVisible(false);
							pieChoice.setVisible(false);
							graphChart.setVisible(false);
							graphChart2.setVisible(false);
							googleMap.setVisible(false);
							locationLabel.setVisible(false);
							locationChoice.setVisible(false);
							radioDay.setVisible(false);
							radioHour.setVisible(false);
							graph2Start.setVisible(false);
							graph2End.setVisible(false);
							startLabel.setVisible(false);
							endLabel.setVisible(false);
							menuIndex = 1;
							break;

						case 4:
						case 5:
						case 6:
						case 7:
							menuTree.getSelectionModel().selectRange(4, 8);
							tabTitle.setText("Piecharts");
							recentTweets.setVisible(false);
							menuTree.setPrefHeight(800);
							dbTable.setVisible(false);
							tableTree.setVisible(false);
							pieChart.setVisible(true);
							pieChoice.setVisible(true);
							graphChart.setVisible(false);
							graphChart2.setVisible(false);
							googleMap.setVisible(false);
							locationLabel.setVisible(false);
							locationChoice.setVisible(false);
							radioDay.setVisible(false);
							radioHour.setVisible(false);
							graph2Start.setVisible(false);
							graph2End.setVisible(false);
							startLabel.setVisible(false);
							endLabel.setVisible(false);
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
							pieChart.setVisible(false);
							pieChoice.setVisible(false);
							googleMap.setVisible(false);
							locationLabel.setVisible(false);
							locationChoice.setVisible(false);
							radioDay.setVisible(true);
							radioHour.setVisible(true);
							menuIndex = 3;
							break;

						case 12:
						case 13:
						case 14:
						case 15:
							menuTree.getSelectionModel().selectRange(12, 16);
							tabTitle.setText("Tweet Locations");
							recentTweets.setVisible(false);
							menuTree.setPrefHeight(800);
							dbTable.setVisible(false);
							tableTree.setVisible(false);
							pieChart.setVisible(false);
							pieChoice.setVisible(false);
							graphChart.setVisible(false);
							graphChart2.setVisible(false);
							googleMap.setVisible(true);
							locationLabel.setVisible(true);
							locationChoice.setVisible(true);
							radioDay.setVisible(false);
							radioHour.setVisible(false);
							graph2Start.setVisible(false);
							graph2End.setVisible(false);
							startLabel.setVisible(false);
							endLabel.setVisible(false);
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
		switch (menuIndex) {
		case 1:
			retrieveTweets();
			fillTableTree();
			break;

		case 2:
			fillPie();
			break;

		case 3:
			if(radioHour.isSelected()){
			
			graphChart2.setVisible(false);
			graphChart.setVisible(true);
			
			fillHourGraph();
			}
			else{
			graphChart.setVisible(false);
			graphChart2.setVisible(true);
			fillDayGraph();
			}
			break;
		case 4:
			fillGoogleMap();
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
						String selectedItem = tableTree.getSelectionModel()
								.getSelectedItem().getValue();
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
							database.closeConnection();
							dbTable.setItems(tableData);

						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				});
		
		

	}

	public void fillPie() throws SQLException {
		if(pieChoice.getValue().equals("view Hashtag Piechart")){
			HashtagCounter hashtagCounter = new HashtagCounter();
			hashtagCounter.extractHashtagsFromDatabase(database);
			ArrayList<Hashtag> hashtagList = hashtagCounter.getHashtags();
			pieData = FXCollections.observableArrayList();
			for (Hashtag hashObject : hashtagList) {
				pieData.add(new PieChart.Data(hashObject.getName(), hashObject.getCount()));

			}
			pieChart.setTitle("Hashtag piechart");
			pieChart.setLegendVisible(false);
			pieChart.setData(pieData);
		}
		else{
			LanguageCounter languageCounter = new LanguageCounter();
			languageCounter.extractLanguagesFromDatabase(database);
			ArrayList<Language> languageList = languageCounter.getLanguageList();
			pieData = FXCollections.observableArrayList();
			for (Language langObject : languageList) {
				pieData.add(new PieChart.Data(langObject.getName(), langObject
						.getCount()));

			}
			pieChart.setTitle("Language piechart");
			pieChart.setLegendVisible(true);
			pieChart.setData(pieData);
		}


	}

	@SuppressWarnings("unchecked")
	public void fillHourGraph() throws SQLException {	
		graphChart.getData().clear();
		TweetCounter tweetCounter = new TweetCounter();
		tweetCounter.extractTimeFromDatabase(database);
		ArrayList<Time> timeList = tweetCounter.getTimeList();

		graphChart.setTitle("Tweet count through time (hours) ");
		graphX.setAutoRanging(false);
		graphY.setAutoRanging(false);
		
		graphX.setLabel("Hours");
		graphY.setLabel("Tweet count");
		graphChart.setCreateSymbols(false);
		graphY.setLowerBound(0);
		graphX.setUpperBound(24);
		graphX.setMinorTickCount(0);
		graphX.setTranslateX(5);
		graphX.setTickUnit(1);
		
        //creating the chart
        
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("Tweets");
        //populating the series with data
        for (Time list : timeList) {
        	series.getData().add(new XYChart.Data(Integer.parseInt(list.getTime()), list.getCount()));
        }

        graphChart.getData().add(series);
	}
	public void fillDayGraph() throws SQLException{
		graphChart2.getData().clear();
		DayCounter dayCounter = new DayCounter();
		dayCounter.extractDaysFromDatabase(database, graph2Start.getText(), graph2End.getText());
		ArrayList<Day> dayList = dayCounter.getDayList();

		graphChart2.setTitle("Tweet count through time (days)");
		graph2X.setAutoRanging(true);
		graph2Y.setAutoRanging(true);
		
		graph2X.setLabel("Days");
		graph2Y.setLabel("Tweet count");
		graphChart2.setCreateSymbols(false);
		
		//graph2X.setUpperBound(24);
		//graph2X.setMinorTickCount(0);
		//graph2X.setTickUnit(1);
		
        //creating the chart
        
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("Tweets");
        //populating the series with data
        for (Day list : dayList) {
        	series.getData().add(new XYChart.Data(list.getDays().toString(), list.getCount()));
        }

        graphChart2.getData().add(series);
		
	}
	
	
	public void fillGoogleMap() throws SQLException{
		
		String url = "http://maps.google.com/maps/api/staticmap?center=Rotterdam&zoom=" + locationChoice.getValue() +"&size=1030x616&maptype=roadmap";
		ResultSet result = database.retrieveData("SELECT Latitude, Longitude FROM message WHERE latitude <> 0");
		while(result.next()){
			url += "&markers=color:red|" + result.getString(1) + "," + result.getString(2);
		}
		database.closeConnection();
		url += "&sensor=false&key=AIzaSyAotNCsfaDSW4jCWp0N5g2jXSIS37FVb_k";
		WebEngine webEngine = googleMap.getEngine();
		webEngine.load(url);
		
	}
	

	
	public void onRadioButtonHour(){
		startLabel.setVisible(false);
		endLabel.setVisible(false);
		graph2Start.setVisible(false);
		graph2End.setVisible(false);
		
		
	} 
	public void onRadioButtonDay(){
		startLabel.setVisible(true);
		endLabel.setVisible(true);
		graph2Start.setVisible(true);
		graph2End.setVisible(true);
		
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


	public void setDatabase(Database database) {
		this.database = database;

	}

}
