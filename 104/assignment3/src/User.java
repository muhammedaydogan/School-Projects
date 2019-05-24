
public class User {

	private String name;
	private int money;

	public User(String name) {
		this.name = name;
		this.money = 0;
	}
	
	public int getMoney() {
		return this.money;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void increaseMoney(int money) {
		this.money += money;
	}
	
	public void decreaseMoney(int money) {
		this.money -= money;
	}
	
	// Preparation for Override 
	public void move(int dice) { System.out.println("Override Error on method User.move(int dice)");}
	public int getPosition() { System.out.println("Override Error on method User.getPosition()");return 0;}
	public int getJailCount() { System.out.println("Override Error on method User.getJailCount()");return 0;}
	public Boolean isInJail() { System.out.println("Override Error on method User.isInJail()");return false;}
	public void increaseJailCount() { System.out.println("Override Error on method User.increaseJailCount()");}
	public void moveToJail() { System.out.println("Override Error on method User.moveToJail()");}
	public int getTotalRailRoadNumber() { System.out.println("Override Error on method User.getTotalRailRoadNumber()"); return 0;}
	public void moveToGo() { System.out.println("Override Error on method User.moveToGo()");}
	public void moveToLeicester() { System.out.println("Override Error on method User.moveToLeicester()");}
	public void move3Back() { System.out.println("Override Error on method User.move3Back()");}
	public void increaseRailRoadNumber() {System.out.println("Override Error on method User.increaseRailRoadNumber()");}
}
