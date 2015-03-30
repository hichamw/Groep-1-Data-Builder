package defaults;

import java.util.Collections;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TweetCounter {

	private ResultSet result;
	private String query;
	private ArrayList<Time> timeList = new ArrayList<Time>();
	private ArrayList<String> info = new ArrayList<String>();

	// private HashMap<String, Integer> countMap = new HashMap<>();
	// private SimpleDateFormat sourceDateFormat = new
	// SimpleDateFormat("HH:mm");
	// private SimpleDateFormat targetDateFormat = new SimpleDateFormat("HH:");

	public void extractTimeFromDatabase(Database database) throws SQLException {
		query = "SELECT SUBSTRING(Date, 12,  2) FROM message ";
		result = database.retrieveData(query);

		while (result.next()) {
			info.add(result.getString(1));
			Collections.sort(info);
		}
		/*
		 * for (String time : info) { Times t = new Times(); t.setTime(time);
		 * t.setCount(1); timeList.add(t); }
		 */
		for (String time : info) {
			boolean exists = false;
			for (Time t : timeList) {
				if (time.equals(t.getTime())) {
					t.increment();
					exists = true;
				}
			}
			if (!exists) {
				Time t = new Time();
				t.setTime(time);
				t.setCount(1);
				timeList.add(t);
			}
		}
		printOutTime();
	}
	public void printOutTime() {
		for (Time list : timeList) {
			// int aInt = Integer.parseInt(timeList);
			String a = " ";
			String y = list.getTime();
			System.out.println("Tussen:" + a + y + ":00" + a + "en" + a + y
					+ ":59" + a + "--" + a + "is het aantal Tweets:" + a
					+ list.getCount());
			// System.out.println(s.getTime() +":00");
			// System.out.println("is printed out:" + s.getCount() + " " +
			// "time(s)");
		}
		System.out.println(" ");

	}
}