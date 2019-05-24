
public class Waiter extends Worker {
	private final int maxWorkAmount = 3;
	
	public Waiter(String name, float salary) {
		super(name,salary);
	}
	
	public int getMaxWorkAmount() {
		return maxWorkAmount;
	}
	
	public void increaseSalary() {
		this.totalOrder++;
	}
	
}
