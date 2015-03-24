public class Language {
	private String name;
	private int count;
	private int percentageCount;
	
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
	public int getPercentageCount() {
		return percentageCount;
	}
	public void setPercentageCount(int percentageCount) {
		this.percentageCount = percentageCount;
	}
	

}
