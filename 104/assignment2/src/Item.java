
public class Item {
	
	private String name;
	private float cost;
	private int amount;
	
	public Item(String name, float cost, int amount) {
		this.name = name;
		this.cost = cost;
		this.amount = amount;
	}
	public Item(String name, float cost) {
		this.name = name;
		this.cost = cost;
	}
	
	public int getAmount() {
		return amount;
	}

	public void decreaseAmount() {
		this.amount--;
	}

	public String getName() {
		return name;
	}

	public float getCost() {
		return cost;
	}
	
	
	
}
