import java.util.ArrayList;

public class Table {

	private int id;
	private int customerCapacity;
	private Boolean isReady = true;
	private String creator;
	private String waiter;
	private ArrayList<Order> orders = new ArrayList<Order>();
	private final int maxOrder = 5;
	private int currOrderNum = 0;
	
	public Table(int customerCapacity, String creator) {
		this.customerCapacity = customerCapacity;
		this.creator = creator;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCurrOrderNum() {
		return currOrderNum;
	}
	
	public void resetTable() {
		this.currOrderNum = 0;
		this.waiter = "";
		this.isReady = true;
		orders.clear();
	}

	public void increase() {
		this.currOrderNum++;
	}

	public int getId() {
		return id;
	}
	
	public Boolean getIsReady() {
		return isReady;
	}

	public void setIsReady(Boolean isReady) {
		this.isReady = isReady;
	}

	public String getWaiter() {
		return waiter;
	}

	public void setWaiter(String waiter) {
		this.waiter = waiter;
	}

	public int getmaxOrder() {
		return maxOrder;
	}

	public int getCustomerCapacity() {
		return customerCapacity;
	}

	public String getCreator() {
		return creator;
	}
	
	public void addOrder(Order order) {
		if(this.currOrderNum >= this.maxOrder) {
			System.out.println("Not allowed to exceed max number of orders!");
			return;
		}
		this.orders.add(order);
		increase();
		
	}
	
	public Order getOrder(int i) {
		return this.orders.get(i);
	}
}
