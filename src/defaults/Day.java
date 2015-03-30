package defaults;

public class Day {
	
	private int count;
	private String days;

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
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
