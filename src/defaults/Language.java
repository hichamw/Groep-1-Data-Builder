package defaults;
public class Language {
	private String name;
	private int count;
	
	//this class stores the values of the different languages used in tweets
	
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
	public void increment(){
		count++;
	}
	

}
