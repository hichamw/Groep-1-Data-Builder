import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class Controller {
	public static Connection conn = null;
	public static void main(String[] args) throws SQLException{
		// TODO Auto-generated method stub
		//zelfde als driver class in telefoonboek project
		//connectToDatabase functie van miner/server kan gebruikt worden om te connecten
		//we hebben nog wel een probleempje aangezien we maar 1 user hebben om in te loggen (en dus server gebruikt deze EN client = errors)
		connectToDatabase();
		Data.extractDataFromDatabase();

	}
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
}
