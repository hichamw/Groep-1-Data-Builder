import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

public class Client {
	private ArrayList<Hashtag> hashtags = new ArrayList<Hashtag>();
	private ResultSet result;
	private String query;
	private String[] test;
	private ArrayList<String> info = new ArrayList<String>();

	public void extractDataFromDatabase(Database database) throws SQLException {
		query = "SELECT Content FROM Twitter WHERE Content LIKE '%#%'";
		result = database.retrieveData(query);

		try {
			while (result.next()) {
				// result.getString() extract een column naar keuze uit onze
				// Twitter database.
				// column 1 = Username column 2 = Name etc.
				// String[] info = new String[1];

				test = result.getString(1).split("[\\s]");
				for (int i = 0; i < test.length; i++) {
					info.add(test[i]);
				}

			}

			info.removeIf(new Predicate<String>() {
				public boolean test(String lang) {
					return !(lang.startsWith("#"));
				}
			});

			for (String hash : info) {
				boolean exists = false;
				for (Hashtag tag : hashtags) {
					if (tag.getName().equals(hash)) {
						tag.increment();
						exists = true;
					}

				}
				if (!exists) {
					Hashtag hashtag = new Hashtag();
					hashtag.setName(hash);
					hashtag.setCount(1);
					hashtags.add(hashtag);
				}
			}

			for (Hashtag tag : hashtags) {
				System.out.println(tag.getName());
				System.out.println(tag.getCount());
				System.out.println();

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
