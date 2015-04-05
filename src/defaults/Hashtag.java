package defaults;
public class Hashtag {
	
	//this class stores the values of the different hashtags used in tweets
	private String name;
	private int count;
	private String date;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void increment() {
		count++;

	}

	public String getDate() {
		// TODO Auto-generated method stub
		return date;
	}
	
	public void setDate(String date) {
		// TODO Auto-generated method stub
		this.date = date;
	}
}
