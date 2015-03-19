import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class Client {
	private ArrayList<Hashtag> hashtags = new ArrayList<Hashtag>();
	private ResultSet result;
	private Database database = new Database();
	private String query;

	

	public void extractDataFromDatabase() throws SQLException {
		query = "SELECT Content FROM Twitter";
		result = database.retrieveData(query);

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
					
					if (info.get(i).charAt(0) == '#') {
						
						String temp = info.get(i);
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
