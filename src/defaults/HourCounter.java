package defaults;

import java.util.Collections;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HourCounter {

	private ResultSet result;
	private String query;
	private ArrayList<Hour> timeList = new ArrayList<Hour>();
	private ArrayList<String> info = new ArrayList<String>();

	//this class stores the time of the tweets sent and puts it in an arraylist
	public void extractTimeFromDatabase(Database database) throws SQLException {
		query = "SELECT SUBSTRING(Date, 12,  2) FROM message";
		result = database.retrieveData(query);

		while (result.next()) {
			info.add(result.getString(1));
			Collections.sort(info);
		}
		database.closeConnection();
		for (String time : info) {
			boolean exists = false;
			for (Hour t : timeList) {
				if (time.equals(t.getTime())) {
					t.increment();
					exists = true;
				}
			}
			if (!exists) {
				Hour t = new Hour();
				t.setTime(time);
				t.setCount(1);
				timeList.add(t);
			}
		}
		//printOutTime();
	}

	public void printOutTime() {
		for (Hour list : timeList) {
			String a = " ";
			String y = list.getTime();
			System.out.println("Tussen:" + a + y + ":00" + a + "en" + a + y
					+ ":59" + a + "--" + a + "is het aantal Tweets:" + a
					+ list.getCount());
		}
		System.out.println(" ");

	}
	
	public ArrayList<Hour> getTimeList(){
		return timeList;		
		
	}
}