
import java.util.ArrayList;

public class Order {
	
	private ArrayList<Item> items = new ArrayList<Item>();
	private final int maxItem = 10;
	private int currItem = 0;
	
	public void increase() {
		this.currItem++;
	}
	public int getMaxItem() {
		return this.maxItem;
	}
	public int getCurrItem() {
		return this.currItem;
	}
	
	public void addItem(String name, float cost) {
		Item item = new Item(name,cost);
		if(getCurrItem() >= getMaxItem()) {
			System.out.println("Not allowed to exceed max no. of max item in a single order!");
		}
		items.add(item);
		increase();
	}
	
	public Item getItem(int i) {
		return this.items.get(i);
	}
}
