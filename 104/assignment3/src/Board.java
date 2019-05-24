
public class Board {
	
	private String[] types;
	
	public Board() {
		this.types = new String[40];
		setSquares();
	}
	
	public String getType(int id) {
		return types[id];
	}
	
	private void setSquares() {
		this.types[0] = "Go";
		this.types[1] = "Property";
		this.types[2] = "CommChest";
		this.types[3] = "Property";
		this.types[4] = "Tax";
		this.types[5] = "Property";
		this.types[6] = "Property";
		this.types[7] = "Chance";
		this.types[8] = "Property";
		this.types[9] = "Property";
		this.types[10] = "Jail";
		this.types[11] = "Property";
		this.types[12] = "Property";
		this.types[13] = "Property";
		this.types[14] = "Property";
		this.types[15] = "Property";
		this.types[16] = "Property";
		this.types[17] = "CommChest";
		this.types[18] = "Property";
		this.types[19] = "Property";
		this.types[20] = "FreeParking";
		this.types[21] = "Property";
		this.types[22] = "Chance";
		this.types[23] = "Property";
		this.types[24] = "Property";
		this.types[25] = "Property";
		this.types[26] = "Property";
		this.types[27] = "Property";
		this.types[28] = "Property";
		this.types[29] = "Property";
		this.types[30] = "GoToJail";
		this.types[31] = "Property";
		this.types[32] = "Property";
		this.types[33] = "CommChest";
		this.types[34] = "Property";
		this.types[35] = "Property";
		this.types[36] = "Chance";
		this.types[37] = "Property";
		this.types[38] = "Tax";
		this.types[39] = "Property";
	}

}
