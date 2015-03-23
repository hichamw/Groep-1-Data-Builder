import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

public class Data {
	private ResultSet result;
	private String query;
	public ArrayList<Language> languageList = new ArrayList<Language>();
	public ArrayList<String> info = new ArrayList<String>();

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

		for(Language langObject : languageList){
			//System.out.println(langObject.getName());
			//System.out.println(langObject.getCount());
			//System.out.println();
			//PieChartLang.main(null);
			
	}

	}
	public DefaultPieDataset createDataset() {
		for (String lang : info) {
			for (Language langObject : languageList) {
				if (lang.equals("nl")) {
					int nl = langObject.getCount();
					System.out.println(nl);
				}
				}}
        DefaultPieDataset result = new DefaultPieDataset();
        result.setValue("Nederlands", 25);
        result.setValue("Engels", 25);
        result.setValue("Spaans", 25);
        result.setValue("Russisch", 25);

        return result;
        
		}
	
}