package defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
//import java.util.Date;

public class DayCounter {
	private ResultSet result;
	private String query;
	private ArrayList<Day> dayList = new ArrayList<Day>();
	private ArrayList<String> info = new ArrayList<String>();
	//private String old_format = ("yyyy/dd/MM");
	//private String new_format = ("dd/MM/yyyy");
	//private String oldDateString = ("dayList");
	//private String newDateString;
	
	//this extracts the dates of the tweets and stores it in an arraylist
	public void extractDaysFromDatabase(Database database, String startDate, String endDate) throws SQLException{
		query = "SELECT SUBSTRING(Date, 1, 10) FROM message WHERE Date BETWEEN " + "'" + startDate + "'" + " AND " + "'" + endDate + "'";
		result = database.retrieveData(query);

		while (result.next()) {
			info.add(result.getString(1));
			Collections.sort(info);
		}
		database.closeConnection();

		for (String day : info) {
			boolean exists = false;
			for (Day d : dayList) {
				if (day.equals(d.getDays())) {
					d.increment();
					exists = true;
				}
			}
			if (!exists) {
				Day d = new Day();
				d.setDays(day);
				d.setCount(1);
				dayList.add(d);
			}
		}
		printOutTime();
	}

	public void printOutTime(){
		for (Day list : dayList) {
			String s = "Number of tweets: ";
			String a = " ";
			System.out.println(list.getDays() + a + "--" +  a + s + a + list.getCount());

		}
		System.out.println(" ");

	}
	
	public ArrayList<Day> getDayList(){
		return dayList;
			
	}
}
