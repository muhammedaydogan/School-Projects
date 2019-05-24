
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Properties {
	
	private ArrayList<Property> properties;
	
	public Properties(String propertyFile) {	
		this.properties = new ArrayList<Property>();
		this.setProperties(propertyFile);
	}
	
	public void setProperties(String propertyFile) {
		
		JSONParser parser = new JSONParser();

		try {
			Object obj = parser.parse(new FileReader(propertyFile));

			JSONObject jsonObj = (JSONObject) obj;

			JSONArray propertyArr = (JSONArray) jsonObj.get("1");

			int i = -1;
			while (++i < propertyArr.size()) {
				JSONObject prop = (JSONObject) propertyArr.get(i);
				int id = Integer.parseInt((String) prop.get("id"));
				String name = (String) prop.get("name");
				int cost = Integer.parseInt((String) prop.get("cost"));
				
				Property property = new Land(id, name, cost);
				this.insertSequentially(property);
			}

			propertyArr = (JSONArray) jsonObj.get("2");
			i = -1;
			while (++i < propertyArr.size()) {
				JSONObject prop = (JSONObject) propertyArr.get(i);
				int id = Integer.parseInt((String) prop.get("id"));
				String name = (String) prop.get("name");
				int cost = Integer.parseInt((String) prop.get("cost"));
				
				Property property = new RailRoad(id, name, cost);
				this.insertSequentially(property);
			}

			propertyArr = (JSONArray) jsonObj.get("3");
			i = -1;
			while (++i < propertyArr.size()) {
				JSONObject prop = (JSONObject) propertyArr.get(i);
				int id = Integer.parseInt((String) prop.get("id"));
				String name = (String) prop.get("name");
				int cost = Integer.parseInt((String) prop.get("cost"));
				
				Property property = new Company(id, name, cost);
				this.insertSequentially(property);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void insertSequentially(Property property) {
		int i = 0;
		while (i < this.properties.size()) {
			if(property.getId() > this.properties.get(i).getId()) {
				i++;
			} else if(property.getId() == this.properties.get(i).getId()) {
				System.out.println("error in function Monopoly.Properties.insertSequentially()");
			} else {
				break;
			}
		}
		this.properties.add(property);
	}
	
	public Property getProperty(int id) {
		int i = 0;
		while (i<this.properties.size()) {
			if(this.properties.get(i).getId() == id) {
				break;
			}else {
				i++;
			}
		}
		return this.properties.get(i);
	}

}