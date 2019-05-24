
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Deck {
	
	private ArrayList<String> commChestCards = new ArrayList<String>();
	private ArrayList<String> chanceCards = new ArrayList<String>();
	private int currentCommChestCardPosition;
	private int currentChanceCardPosition;
	
	public Deck(String cardFile) {
		this.currentChanceCardPosition = 0;
		this.currentCommChestCardPosition = 0;
		this.setDeck(cardFile);
	}
	
	private void setDeck(String cardFile) {
		
		JSONParser parser = new JSONParser();

		try {

			Object obj = parser.parse(new FileReader(cardFile));

			JSONObject jsonObj = (JSONObject) obj;

			JSONArray cardArr = (JSONArray) jsonObj.get("chanceList");

			int i = -1;
			while (++i < cardArr.size()) {
				JSONObject card = (JSONObject) cardArr.get(i);
				String item = (String) card.get("item");
				this.chanceCards.add(item);
			}

			cardArr = (JSONArray) jsonObj.get("communityChestList");

			i = -1;
			while (++i < cardArr.size()) {
				JSONObject card = (JSONObject) cardArr.get(i);
				String item = (String) card.get("item");
				this.commChestCards.add(item);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public String getCommChestCard() {
		if(this.currentCommChestCardPosition == this.commChestCards.size()) {
			this.currentCommChestCardPosition = 0;
		}
		return this.commChestCards.get(this.currentCommChestCardPosition++);
	}
	
	public String getChanceCard() {
		if(this.currentChanceCardPosition == this.chanceCards.size()) {
			this.currentChanceCardPosition = 0;
		}
		return this.chanceCards.get(this.currentChanceCardPosition++);
	}
}
