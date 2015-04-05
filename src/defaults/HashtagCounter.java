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

	//this method extracts tweets based on hashtags and stores it in an arraylist
	public void extractHashtagsFromDatabase(Database database) throws SQLException {
		query = "SELECT Content FROM message WHERE Content LIKE '%#%'";
		result = database.retrieveData(query);

		try {
			while (result.next()) {
				rawData = result.getString(1).toLowerCase().split("[\\s]");
				for (int i = 0; i < rawData.length; i++) {
					info.add(rawData[i]);
				}

			}
			
			database.closeConnection();
			info.removeIf(new Predicate<String>() {
				public boolean test(String hash) {
					return !(hash.startsWith("#")) ||hash.startsWith("#euromast") || hash.startsWith("#Euromast");
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

//			for (Hashtag tag : hashtags) {
//				System.out.println(tag.getName());
//				System.out.println(tag.getCount());
//				System.out.println();
//
//			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<Hashtag> getHashtags(){		
		return hashtags;
		
	}

}
