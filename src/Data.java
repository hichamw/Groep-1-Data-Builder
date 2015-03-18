import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Data {
	private static Connection conn = null;
	private static ArrayList <String> data = new ArrayList <String>();
	private static ResultSet result;
	private static PreparedStatement statement;
	
	public static void connectToDatabase(){
		
		String host = "sql3.freemysqlhosting.net";
		String DBName = "sql369437";
		String user = "sql369437";
		String password = "yH1%hH1*";
		String encPassword = URLEncoder.encode(password);
		try {
            conn = DriverManager.getConnection("jdbc:mysql://" + host +"/"+ DBName +"?user=" + user + "&password=" + encPassword + "");
            System.out.println("Database connected!");
           
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
	
	
    }
	
	public static void extractDataFromDatabase() throws SQLException {
		statement = conn.prepareStatement("SELECT Language FROM Twitter");		
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

