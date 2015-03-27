package defaults;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class Database {
	private PreparedStatement statement;
	private Connection conn = null;
	private ResultSet result;
	
	public void connectToDatabase(){
		String host = "145.24.222.208:8124";
		String DBName = "dataminers";
		String user = "Client";
		String password = "databuilder";
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
	
	public ResultSet retrieveData(String query) throws SQLException{
		
		connectToDatabase();
		statement = conn.prepareStatement(query);		
		result = statement.executeQuery();
		return result;
		
	}
	
	public void closeConnection() throws SQLException{
		
		conn.close();
	}

}
