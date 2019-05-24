
public class Employer extends Worker {
	private final int maxWorkAmount = 2;
	
	public Employer(String name, float salary) {
		super(name,salary);
	}

	public int getMaxWorkAmount() {
		return maxWorkAmount;
	}
	
	public void increaseSalary() {
		this.totalOrder++;
	}
}
