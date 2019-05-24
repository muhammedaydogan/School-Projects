
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Monopoly {

	private Deck deck;
	private User user1, user2, banker;
	private Board board;
	private Properties properties;

	public Monopoly(String propertyFile, String cardFile) {
		this.deck = new Deck(cardFile);
		this.user1 = new Player("Player 1");
		this.user2 = new Player("Player 2");
		this.banker = new Banker("Banker");
		this.board = new Board();
		this.properties = new Properties(propertyFile);
	}

	public void play(String commandFileName, String outputFileName) {

		try (BufferedReader commandFile = new BufferedReader(new FileReader(commandFileName))) {
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName,false));

			String line;
			while ((line = commandFile.readLine()) != null) {
				if (line.equals("show()")) {
					writer.write("-----------------------------------------------------------------------------------------------------------\n");
					writer.write(user1.getName() + "\t" + user1.getMoney() + "\t" + "have: " + this.getOwnedProperties(user1.getName()) + "\n");
					writer.write(user2.getName() + "\t" + user2.getMoney() + "\t" + "have: " + this.getOwnedProperties(user2.getName()) + "\n");
					writer.write(banker.getName() + "\t" + banker.getMoney() + "\n");
					if (user1.getMoney() > user2.getMoney()) {
						writer.write("Winner Player 1" + "\n");
					} else if (user1.getMoney() < user2.getMoney()) {
						writer.write("Winner Player 2" + "\n");
					} else {
						writer.write("scoreless" + "\n");
					}
					writer.write("-----------------------------------------------------------------------------------------------------------" + "\n");
				} else {
					String action = "";
					String[] command = line.split(";");
					if (command[0].equals(user1.getName())) {
						int dice = Integer.parseInt(command[1]);
						
						if(user1.isInJail()) {
							action = "in jail (count=" + String.valueOf(user1.getJailCount()) +")";
							user1.increaseJailCount();
						} else {
							if(user1.getPosition() + dice > 39) {
								banker.decreaseMoney(200);
							}
							user1.move(dice);
							action = this.doSquareAction(dice, this.user1, this.user2);
						}
						int position = user1.getPosition() + 1;
						writer.write(user1.getName() + "\t" + dice + "\t" + position + "\t" + user1.getMoney() + "\t" + user2.getMoney() + "\t");
						writer.write("Player 1 " + action + "\n");
						
					} else if (command[0].equals(user2.getName())) {
						int dice = Integer.parseInt(command[1]);
						
						if(user2.isInJail()) {
							action = "in jail (count=" + String.valueOf(user2.getJailCount()) +")";
							user2.increaseJailCount();
						} else {
							if(user2.getPosition() + dice > 39) {
								banker.decreaseMoney(200);
							}
							user2.move(dice);
							action = this.doSquareAction(dice, this.user2, this.user1);
						}
						int position = user2.getPosition() + 1;
						writer.write(user2.getName() + "\t" + dice + "\t" + position + "\t" + user1.getMoney() + "\t" + user2.getMoney() + "\t");
						writer.write("Player 2 " + action + "\n");
					}
					if (action.equals("goes bankrupt")) {
						break;
					}
				}
			}
			writer.write("-----------------------------------------------------------------------------------------------------------" + "\n");
			writer.write(user1.getName() + "\t" + user1.getMoney() + "\t" + "have: " + this.getOwnedProperties(user1.getName()) + "\n");
			writer.write(user2.getName() + "\t" + user2.getMoney() + "\t" + "have: " + this.getOwnedProperties(user2.getName()) + "\n");
			writer.write(banker.getName() + "\t" + banker.getMoney() + "\n");
			if (user1.getMoney() > user2.getMoney()) {
				writer.write("Winner Player 1" + "\n");
			} else if (user1.getMoney() < user2.getMoney()) {
				writer.write("Winner Player 2" + "\n");
			} else {
				writer.write("scoreless" + "\n");
			}
			writer.write("-----------------------------------------------------------------------------------------------------------");
			commandFile.close();
			writer.close();
		} catch (IOException e) {
			System.out.println("Cannot open command.txt");
			return;
		}
	}
	
	public String doSquareAction(int dice, User user1, User user2) {
		String action = new String("");
		switch (this.board.getType(user1.getPosition())) {
		case "Go":
			action += "is in GO square";
			break;

		case "Property":
			if(properties.getProperty(user1.getPosition()).getOwner().equals("")) {
				//1. Buy
				if(user1.getMoney() >= properties.getProperty(user1.getPosition()).getCost()) {
					//1.1. Successfull Trade
					action += "bought " + properties.getProperty(user1.getPosition()).getName();
					properties.getProperty(user1.getPosition()).setOwner(user1.getName());
					user1.decreaseMoney(properties.getProperty(user1.getPosition()).getCost());
					banker.increaseMoney(properties.getProperty(user1.getPosition()).getCost());
					if(user1.getPosition() == 5 || user1.getPosition() == 15 || user1.getPosition() == 25 || user1.getPosition() == 35) {
						user1.increaseRailRoadNumber();
					}
				} else {
					//1.2. Goes Bankrupt (cannot afford to buy)
					action += "goes bankrupt";
				}
			} else if(properties.getProperty(user1.getPosition()).getOwner().equals(user2.getName())) {
				//2. Pay rent
				int rent = 0, position = user1.getPosition();
				if(position == 12 || position == 28) {
					// company
					rent = this.calculateCompanyRent(dice);
				} else if (position == 5 || position == 15 || position == 25 || position == 35) {
					// railroad
					rent = this.calculateRailRoadRent(user2.getTotalRailRoadNumber());
				} else {
					// land
					rent = this.calculateLandRent(properties.getProperty(user1.getPosition()).getCost());
				}
				if(user1.getMoney() >= rent) {
					//2.1 Successfull Trade
					user1.decreaseMoney(rent);
					user2.increaseMoney(rent);
					action += "paid rent for " + properties.getProperty(user1.getPosition()).getName();
				} else {
					//2.2. Goes Bankrupt (cannot afford to pay rent)
					action += "goes bankrupt";
				}
			} else {
				//1.2. Pass without buy, His Own Property.
				action += "has  " + properties.getProperty(user1.getPosition()).getName();
			}
			break;

		case "CommChest":
			String card1 = deck.getCommChestCard();
			if(card1.equals("Advance to Go (Collect $200)")) {
				user1.moveToGo();
				banker.decreaseMoney(200);
				action = "draw Advance to Go (Collect $200)";
			} else if(card1.equals("Bank error in your favor - collect $75")) {
				user1.increaseMoney(75);
				banker.decreaseMoney(75);
				//action = "draw Bank error in your favor - collect $75";
			} else if(card1.equals("Doctor's fees - Pay $50")) {
				user1.decreaseMoney(50);
				banker.increaseMoney(50);
				//action = "";
			} else if(card1.equals("It is your birthday Collect $10 from each player")) {
				user1.increaseMoney(10);
				user2.decreaseMoney(10);
				//action = "";				
			} else if(card1.equals("Grand Opera Night - collect $50 from every player for opening night seats")) {
				user1.increaseMoney(50);
				user2.decreaseMoney(50);
				//action = "";
			} else if(card1.equals("Income Tax refund - collect $20")) {
				user1.increaseMoney(20);
				banker.decreaseMoney(20);
				//action = "";
			} else if(card1.equals("Life Insurance Matures - collect $100")) {
				user1.increaseMoney(100);
				banker.decreaseMoney(100);
				//action = "";
			} else if(card1.equals("Pay Hospital Fees of $100")) {
				user1.decreaseMoney(100);
				banker.increaseMoney(100);
				//action = "";
			} else if(card1.equals("Pay School Fees of $50")) {
				user1.decreaseMoney(50);
				banker.increaseMoney(50);
				//action = "";
			} else if(card1.equals("You inherit $100")) {
				user1.decreaseMoney(100);
				banker.decreaseMoney(100);
				//action = "";
			} else if(card1.equals("From sale of stock you get $50")) {
				user1.increaseMoney(50);
				banker.decreaseMoney(50);
				//action = "";
			} else {
				action += "Unknown Card : " + card1;
			}
			break;

		case "Chance":
			String card2 = deck.getChanceCard();
			if(card2.equals("Advance to Go (Collect $200)")) {
				user1.moveToGo();
				banker.decreaseMoney(200);
				action = "draw Advance to Go (Collect $200)";
			} else if(card2.equals("Advance to Leicester Square")) {
				if(user1.getPosition() > 26) {
					banker.decreaseMoney(200);
				}
				user1.moveToLeicester();
				action += "draw Advance to Leicester Square, ";
				action += user1.getName() + " " + this.doSquareAction(dice, user1, user2);
			} else if(card2.equals("Go back 3 spaces")) {
				user1.move3Back();
				action += "draw Go back 3 spaces, ";
				action += user1.getName() + " " + this.doSquareAction(dice, user1, user2);
			} else if(card2.equals("Pay poor tax of $15")) {
				user1.decreaseMoney(15);
				banker.increaseMoney(15);
				action = "draw Pay poor tax of $15";
			} else if(card2.equals("Your building loan matures - collect $150")) {
				user1.increaseMoney(150);
				banker.decreaseMoney(150);
				action = "draw Your building loan matures - collect $150";
			} else if(card2.equals("You have won a crossword competition - collect $100 ")) {
				user1.increaseMoney(100);
				banker.decreaseMoney(100);
				action = "draw You have won a crossword competition - collect $100 ";
			} else {
				action += "Unknown Card : \"" + card2 +"\"";
			}
			break;

		case "Tax":
			user1.decreaseMoney(100);
			banker.increaseMoney(100);
			action += "paid Tax";
			break;

		case "Jail":
			user1.moveToJail();
			action = "went to jail";
			break;

		case "GoToJail":
			user1.moveToJail();
			action = "went to jail";
			break;

		case "FreeParking":
			action = "is in Free Parking";
			break;

		default:
			System.out.println("Error : Square Type Detection");
			break;
		}
		
		return action;
	}
	
	public String getOwnedProperties(String name) {
		String owned = new String(""), ownedRailRoad = new String(""), ownedCompany = new String("");
		int i = 0,j=0;
		while (i<40) {
			if(board.getType(i).equals("Property")) {
				if(properties.getProperty(i).getOwner().equals(name)) {
					j++;
				}
			}
			i++;
		}
		i = 0;
		while (i<40) {
			if(board.getType(i).equals("Property")) {
				if(properties.getProperty(i).getOwner().equals(name)) {
					int position = properties.getProperty(i).getId();
					if(position == 5 || position == 15 || position == 25 || position == 35) {
						j--;
						ownedRailRoad += properties.getProperty(i).getName();
						if(j!=0) {
							ownedRailRoad += ", ";
						}
						i++;
						continue;
					} else if(position == 12 || position == 28) {
						j--;
						ownedCompany += properties.getProperty(i).getName();
						if(j!=0) {
							ownedCompany += ", ";
						}
						i++;
						continue;
					} else {
						j--;
						owned += properties.getProperty(i).getName();
						if(j!=0) {
							owned += ", ";
						}	
					}
				}
			}
			i++;
		}
		return owned + ownedRailRoad + ownedCompany;
	}
	
	public int calculateRailRoadRent(int othersRailNum) {
		if(othersRailNum > 0 && othersRailNum < 28) {
			return 25 * othersRailNum;
		}
		System.out.println("error: rail-rent overflow");
		return 0;
	}
	
	public int calculateLandRent(int landCost) {
		int cost = 0;
		if (landCost < 0) {
			System.out.println("error : Cost of Land Error");
		} else if (landCost <= 2000) {
			cost = (landCost * 40 / 100);
		} else if (landCost <= 3000) {
			cost = (landCost * 30 / 100);
		} else if (landCost <= 4000) {
			cost = (landCost * 35 / 100);
		} else {
			System.out.println("error : Cost of Land Upper Bound Error");
		}
		return cost;
	}
	
	public int calculateCompanyRent(int dice) {
		if(dice > 0 && dice < 13) {
			return dice * 4;
		}
		System.out.println("error : dice overflow");
		return 0;
	}
}
