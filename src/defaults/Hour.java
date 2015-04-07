package defaults;

public class Hour {

	private int count;
	private String time;
	
	//this class stores information about the time of the tweets
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public void increment(){
		count++;
	}
}