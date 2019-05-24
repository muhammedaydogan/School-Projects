//package bbm104quiz2;

public class Member {

	private String name;
    private String surname;
    private float weight;
	private float height;
    private int id;

    public Member(String name, String surname, float weight, float height){
        this.name = name;
        this.surname = surname;
        this.weight = weight;
        this.height = height;
    }

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
	
	public void display() {
			System.out.println("Id:" + getId() + " Name:" + getName() + " Surname:" + getSurname() +
					" Weight:" + getWeight() + " Height:" + getHeight());
	}
	
	public void weightStatus() {
		float ibm = BMI();
		if ( ibm < 18.5 ) {
			System.out.println("Underweight");
		}
		else if ( ibm < 24.9 ) {
			System.out.println("Normal");
		}
		else if ( ibm < 29.9 ) {
			System.out.println("Overweight");
		}
		else if ( ibm < 34.9 ) {
			System.out.println("Obese");
		}
		else {
			System.out.println("Extremely Obese");
		}
	}
	
	private float BMI() {
		float ibm = getWeight()  /   ( getHeight() * getHeight() )  ;
		return ibm;
	}

}
