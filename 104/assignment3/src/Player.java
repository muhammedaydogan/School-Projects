
public class Player extends User {

	private int position;
	private int numberOfTotalRailRoads;
	private int jailCount;
	private Boolean inJail;
	
	public Player(String name) {
		super(name);
		this.increaseMoney(15000);
		this.position = 0;
		this.numberOfTotalRailRoads = 0;
		this.jailCount = 0;
		this.inJail = false;
	}
	
	public int getJailCount() {
		return this.jailCount;
	}
	
	public Boolean isInJail() {
		return this.inJail;
	}
	
	public void increaseJailCount() {
		this.jailCount++;
		if(this.jailCount == 4) {
			this.jailCount = 0;
			this.inJail = false;
		}
	}
	
	public void move(int dice) {
		this.position += dice;
		if(this.position > 39) {
			this.position -= 40;
			this.increaseMoney(200);
		}
	}
	
	public void moveToGo() {
		this.position = 0;
		this.increaseMoney(200);
	}
	
	public void moveToJail() {
		this.position = 10;
		this.jailCount = 1;
		this.inJail = true;
	}
	
	public void moveToLeicester() {
		if(this.getPosition() > 26) {
			this.increaseMoney(200);
		}
		this.position = 26;
	}
	
	public void move3Back() {
		this.position -= 3;
		if(this.position < 0) {
			this.position += 40;
		}
	}
	
	public int getPosition() {
		return this.position;
	}
	
	public int getTotalRailRoadNumber() {
		return this.numberOfTotalRailRoads;
	}
	
	public void increaseRailRoadNumber() {
		this.numberOfTotalRailRoads++;
	}

}
