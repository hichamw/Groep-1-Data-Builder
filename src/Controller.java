import java.sql.SQLException;

public class Controller {


	public static void main(String[] args) throws SQLException {
		Data data = new Data();
		Client client = new Client();
		Database database = new Database();
		database.connectToDatabase();
		//data.extractDataFromDatabase(database);
		client.extractDataFromDatabase(database);

	}

}
