import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Client {
	private Connection conn = null;
	private ArrayList<Hashtag> hashtags = new ArrayList<Hashtag>();
	private ResultSet result;
	private PreparedStatement statement;

	public void connectToDatabase() {
		String host = "sql3.freemysqlhosting.net";
		String DBName = "sql369437";
		String user = "sql369437";
		String password = "yH1%hH1*";
		String encPassword = URLEncoder.encode(password);
		try {
			conn = DriverManager.getConnection("jdbc:mysql://" + host + "/"
					+ DBName + "?user=" + user + "&password=" + encPassword
					+ "");
			System.out.println("Database connected!");

		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}  

	}

	public void extractDataFromDatabase() throws SQLException {
		statement = conn.prepareStatement("SELECT Content FROM Twitter");
		result = statement.executeQuery();

		try {
			while (result.next()) {
				// result.getString() extract een column naar keuze uit onze
				// Twitter database.
				// column 1 = Username column 2 = Name etc.
				//String[] info = new String[1];
				ArrayList<String> info = new ArrayList<String>();
				String[] test = result.getString(1).split("[\\s]");
				info = (ArrayList<String>) Arrays.asList(test);
				for (int i = 0; i <= info.size(); i++) {
					
					if (info[i].charAt(0) == '#') {
						
						String temp = info[i];
						for (Hashtag h : hashtags) {
							if (h.getName().equals(temp)) {
								h.increment();

							} else {
								hashtags.add(h);
							}

						}

					}
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (Hashtag h : hashtags) {

			System.out.println(h.getName());
			System.out.println(h.getCount());

		}
	}

}
