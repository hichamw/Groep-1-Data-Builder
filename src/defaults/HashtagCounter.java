package defaults;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.function.Predicate;

public class HashtagCounter {
	private ArrayList<Hashtag> hashtags = new ArrayList<Hashtag>();
	private ResultSet result;
	private String query;
	private String[] rawData;
	private ArrayList<String> info = new ArrayList<String>();

	public void extractHashtagsFromDatabase(Database database) throws SQLException {
		query = "SELECT Content FROM Twitter WHERE Content LIKE '%#%'";
		result = database.retrieveData(query);

		try {
			while (result.next()) {
				rawData = result.getString(1).split("[\\s]");
				for (int i = 0; i < rawData.length; i++) {
					info.add(rawData[i]);
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
					if (hash.equals(tag.getName())) {
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
