//package bbm104quiz2;

public class SportCenter {

	private int num;
	private Member memb[];
	
	
	public SportCenter() {
		super();
		this.num = 0;
		this.memb = new Member[10];
	}
	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public Member[] getMemb() {
		return memb;
	}

	public void setMemb(Member[] memb) {
		this.memb = memb;
	}
	public void addMember(Member m) {
		
		Member membL[]= getMemb();
		int index = getNum();
		for(int j = 0; j< getNum();j++) {
			if(getMemb()[j].getName().equalsIgnoreCase(m.getName()) &&
					getMemb()[j].getSurname().equalsIgnoreCase(m.getSurname()) ) {
				System.out.println("Already exits..");
				return;
			}
		}
		membL[index++] = m;
		membL[index-1].setId(index-1);
		setNum(index);
	}

	public void search(String name, String surname) {
		
		for(int i=0;i<getNum();i++) {
			if(getMemb()[i].getName().equalsIgnoreCase(name) &&
					getMemb()[i].getSurname().equalsIgnoreCase(surname)) {
				getMemb()[i].weightStatus();
				return;
			}
		}
	}
	
	public void printAllMembers() {
		for(int j = 0;j<getNum();j++) {
			getMemb()[j].display();
		}
	}
}
