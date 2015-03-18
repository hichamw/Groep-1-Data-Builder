import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Data {
	private static ArrayList <String> data = new ArrayList <String>();
	private static ResultSet result;
	private static PreparedStatement statement;
	
	public static void extractDataFromDatabase() throws SQLException {
		statement = Controller.conn.prepareStatement("SELECT Language FROM Twitter");		
		result = statement.executeQuery();
		int nl = 0;
		int en = 0;
		int ru = 0;
		while(result.next()){
			// result.getString() extract een column naar keuze uit onze Twitter database.
			// column 1 = Username column 2 = Name etc.		
			if(result.getString(1).equals("nl")){
				nl++;
			}
			if(result.getString(1).equals("en")){
				en++;
			}
			if(result.getString(1).equals("ru")){
				ru++;
			}
			}
			System.out.println("NL " + nl);
			System.out.println("EN " + en);
			System.out.println("RU " + ru);
		}

		

		
}

