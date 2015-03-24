import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Data {
	private ResultSet result;
	private String query;
<<<<<<< HEAD
	public static ArrayList<Language> languageList = new ArrayList<Language>();
	public static ArrayList<String> info = new ArrayList<String>();
=======
	private ArrayList<Language> languageList = new ArrayList<Language>();
	private ArrayList<String> info = new ArrayList<String>();
>>>>>>> parent of 681d4de... added pie chart class, small test in data.java met de gegevens ophalen

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

<<<<<<< HEAD
	}
	public void RunThePie(Database database) throws SQLException{
		extractLanguagesFromDatabase(database);
		PieChartLang demo = new PieChartLang("Talen", "Welke taal tweet het meest?");
        demo.pack();
        demo.setVisible(true);
	}
	

	public static PieDataset createDataset() {
		//TODO fix data, piechart is wrong
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
					System.out.println("a" + nl);
				}
				else if (lang.equals("en")) {
					en = langObject.getCount();
					System.out.println("b" + en);
				}
				else if (lang.equals("es")) {
					es = langObject.getCount();
					System.out.println("c" + es );
				}
				else if (lang.equals("ru")) {
					ru = langObject.getCount();
					System.out.println("d" + ru);
				}
			}
			total = nl + en + es + ru;
			System.out.println("e" + total);
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
=======
		for(Language langObject : languageList){
			System.out.println(langObject.getName());
			System.out.println(langObject.getCount());
			System.out.println();
		}

	}

}
>>>>>>> parent of 681d4de... added pie chart class, small test in data.java met de gegevens ophalen
