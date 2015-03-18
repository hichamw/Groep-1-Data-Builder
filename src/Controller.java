import java.sql.SQLException;



public class Controller {

	public static void main(String[] args) throws SQLException{
		// TODO Auto-generated method stub
		//zelfde als driver class in telefoonboek project
		//connectToDatabase functie van miner/server kan gebruikt worden om te connecten
		//we hebben nog wel een probleempje aangezien we maar 1 user hebben om in te loggen (en dus server gebruikt deze EN client = errors)
		Client.connectToDatabase();
		Client.extractDataFromDatabase();

	}
}
