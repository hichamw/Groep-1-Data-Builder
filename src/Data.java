import java.sql.ResultSet;
import java.sql.SQLException;



public class Data {
	private  ResultSet result;
	private Database database = new Database();
	private String query; 
	
	public void extractDataFromDatabase() throws SQLException {
		query = "SELECT Language FROM Twitter";
		result = database.retrieveData(query);
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

