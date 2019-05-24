
public class Property {

	private int id;
	private String name;
	private int cost;
	private String owner;
	
	public Property(int id, String name, int cost) {
		this.id = id-1;
		this.name = name;
		this.cost = cost;
		this.owner = "";
	}
	public int getCost() {
		return this.cost;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getOwner() {
		return this.owner;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
}
