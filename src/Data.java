import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

public class Data {
	private ResultSet result;
	private String query;
	public static ArrayList<Language> languageList = new ArrayList<Language>();
	public static ArrayList<String> info = new ArrayList<String>();

	public void extractLanguagesFromDatabase(Database database)
			throws SQLException {
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

		for (Language langObject : languageList) {
			// System.out.println(langObject.getName());
			// System.out.println(langObject.getCount());
			// System.out.println();
			PieChartLang.main(null);

		}

	}

	public static PieDataset createDataset() {
		for (String lang : info) {
			int nl = 0;
			int en = 0;
			int es = 0;
			int ru = 0;
			int total = 0;
			int nlPercent = 0;
			int enPercent = 0;
			int esPercent = 0;
			int ruPercent = 0;
			for (Language langObject : languageList) {
				if (lang.equals("nl")) {
					nl = langObject.getCount();
				}
				if (lang.equals("en")) {
					en = langObject.getCount();
				}
				if (lang.equals("es")) {
					es = langObject.getCount();
				}
				if (lang.equals("ru")) {
					ru = langObject.getCount();
				}
			}
			total = nl + en + es + ru;
			 nlPercent = nl/total*100;
			 enPercent = en/total*100;
			 esPercent = es/total*100;
			 ruPercent = ru/total*100;

			DefaultPieDataset result = new DefaultPieDataset();
			result.setValue("Nederlands", nlPercent);
			result.setValue("Engels", enPercent);
			result.setValue("Spaans", esPercent);
			result.setValue("Russisch", ruPercent);

			return result;
		}
		return null;
	}

}