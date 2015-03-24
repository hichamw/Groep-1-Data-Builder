import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Data {
	private ResultSet result;
	private String query;
	private ArrayList<Language> languageList = new ArrayList<Language>();
	private ArrayList<String> info = new ArrayList<String>();

	public void extractLanguagesFromDatabase(Database database) throws SQLException {
		query = "SELECT Language FROM Twitter";
		result = database.retrieveData(query);
		
		while (result.next()) {
			info.add(result.getString(1));
		}

		for (String lang : info) {
			boolean exists = false;
			for (Language langObject : languageList) {
				if (lang.equals(langObject.getName())) {
					langObject.increment();
					exists = true;
				}
			}
			if (!exists) {
				Language language = new Language();
				language.setName(lang);
				language.setCount(1);
				languageList.add(language);

			}

		}
		WriteLanguages();
		
		}

	
	public void WriteLanguages(){
		for(Language langObject : languageList){
			System.out.println(langObject.getName());
			System.out.println(langObject.getCount());
			System.out.println();
	}
	

	}
	}
