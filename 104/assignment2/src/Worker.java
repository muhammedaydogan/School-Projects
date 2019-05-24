
public class Worker {
	private String name;
	protected float salary;
	private int workAmount = 0;
	protected int totalOrder = 0;
	
	public Worker(String name, float salary) {
		this.name = name;
		this.salary = salary;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getSalary() {
		return salary;
	}
	public void setSalary(float salary) {
		this.salary = salary;
	}
	public int getWorkAmount() {
		return workAmount;
	}
	public void increseWorkAmount() {
		this.workAmount++;
	}
	
	public int getTotalOrder() {
		return this.totalOrder;
	}
	
	public void checkout() {
		this.workAmount--;
	}
	
}
