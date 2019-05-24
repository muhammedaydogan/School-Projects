
import java.lang.reflect.Array;
//import java.util.ArrayList;
//import java.util.Collections;
import java.util.*;

public class Restaurant {

	private ArrayList<Employer> emps = new ArrayList<Employer>();
	private ArrayList<Waiter> wtrs = new ArrayList<Waiter>();
	private ArrayList<Item> items = new ArrayList<Item>();
	private ArrayList<Table> tables = new ArrayList<Table>();
	public final int maxEmp = 5;
	private int currEmpNum = 0;
	public final int maxWtr = 5;
	private int currWtrNum = 0;
	public final int maxTbl = 5;
	private int currTblNum = 0;
	
	public int getCurrEmpNum() {
		return currEmpNum;
	}
	
	public int getCurrWtrNum() {
		return currWtrNum;
	}
	
	public int getCurrTblNum() {
		return currTblNum;
	}
	
	public void increaseEmp() {
		this.currEmpNum++;
	}
	
	public void increaseWtr() {
		this.currWtrNum++;
	}
	
	public void increaseTbl() {
		this.currTblNum++;
	}
	
	public void addItem(Item item) {
		this.items.add(item);
	}
	
	public void addEmployer(Employer emp) {
		if(maxEmp > getCurrEmpNum()) {
			this.emps.add(emp);
			increaseEmp();
		}
	}
	
	public void addWaiter(Waiter wtr) {
		if(maxWtr > getCurrWtrNum()) {
			this.wtrs.add(wtr);
			increaseWtr();
		}
	}
	
	public void createTable(Table tbl) {
		int found = -1,i;
		for(i=0;i<this.emps.size();i++) {
			if (this.emps.get(i).getName().equalsIgnoreCase(tbl.getCreator())  ) {
				found = i;
				break;
			}
		}
		if(found == -1) {
			System.out.println("There is no employer named " + tbl.getCreator());
		}
		else if(maxTbl <= getCurrTblNum()) {
			System.out.println("Not allowed to exceed max. number of tables, MAX_TABLES");
		}
		else if(this.emps.get(found).getMaxWorkAmount() <= this.emps.get(found).getWorkAmount()) {
			System.out.println(this.emps.get(found).getName() + " has already created ALLOWED_MAX_TABLES tables!");
		}
		else {
			tbl.setId(getCurrTblNum());
			this.tables.add(tbl);
			this.emps.get(found).increseWorkAmount();
			this.emps.get(found).increaseSalary();
			increaseTbl();
			System.out.println("A new table has succesfully been added");
		}
		
	}

	public int getWtr(String name) {
		int j=-1;
		for(int i = 0;i<getCurrWtrNum();i++) {
			if(this.wtrs.get(i).getName().equalsIgnoreCase(name)) {
				j = i;
				break;
			}
		}
		return j;
	}

	public int getTbl(int cstmrNum) {
		int j = -1;
		for(int i = 0;i<getCurrTblNum();i++) {
			if(this.tables.get(i).getIsReady() == true) {
				j = i;
				break;
			}
		}
		return j;
	}
	
	public int getItem(String itemName) {
		int i = -1;
		for(int j=0; j<this.items.size(); j++) {
			if(this.items.get(j).getName().equalsIgnoreCase(itemName)) {
				i = j;
				break;
			}
		}
		return i;
	}
	
	public float getItemCost(String itemName) {
		float cost = 0;
		for(int j = 0;j<this.items.size();j++) {
			if(this.items.get(j).getName().equalsIgnoreCase(itemName)) {
				cost = this.items.get(j).getCost();
				break;
			}
		}
		return cost;
	}

	public ArrayList<Integer> getTbl2(int cstmrNum) {
		ArrayList<Integer> freetables = new ArrayList<Integer>();
		for(int i = 0;   i < getCurrTblNum();   i++) {
			if(this.tables.get(i).getIsReady() == true) {
				freetables.add(i);
			}
		}
		return freetables;
	}
	
	public void newOrder(String name,int cstmrNum, String items) {
		
		int g = getWtr(name);
		if (g == -1){
			System.out.println("There is no waiter named " + name);
			return;
		}
		if (this.wtrs.get(g).getWorkAmount() >= this.wtrs.get(g).getMaxWorkAmount()) {
			System.out.println(this.wtrs.get(g).getWorkAmount());
			System.out.println(this.wtrs.get(g).getMaxWorkAmount());
			System.out.println("Not allowed to service max. number of tables, MAX_TABLE_SERVICES");
			return;
		}
		int  t = getTbl(cstmrNum);
		if (t == -1) {
			System.out.println("There is no appropriate table for this order!*"); // No table
			return;
		}
		if (this.tables.get(t).getCustomerCapacity() < cstmrNum) {// There is a table. But Capacity is not enough.
			int found=0;
			int i = 0;
			ArrayList<Integer> freeTables = getTbl2(cstmrNum);
			while(i<freeTables.size()) {
				if(this.tables.get( freeTables.get(i) ).getCustomerCapacity() >= cstmrNum) {
					found = 1;
					break;
				}
				i++;
			}
			if(found == 0) {
				System.out.println("There is no appropriate table for this order!"); // No table
				return;
			}
			t = freeTables.get(i);
		}
		System.out.println("Table (= ID " + t + ") has been taken into service");
		String[] itemsSplitted = items.split(":|\\-");
		Order order = new Order();
		for(int i=0; i<itemsSplitted.length; i++ ) {
			
			String itemName = itemsSplitted[i];
			i++;
			int j = getItem(itemName);
			int amount = Integer.parseInt(itemsSplitted[i]);
			while(amount>0) {
				if(j==-1) {
					System.out.println("Unknown item Waffle");
				}
				else if(this.items.get(j).getAmount() == 0) {
					System.out.println("Sorry! No " + itemName + " in the stock!");
				}
				else {
					System.out.println("Item " + itemName + " added into order");
					this.items.get(j).decreaseAmount();
					order.addItem(itemName,getItemCost(itemName));
				}
				amount--;
			}
		}
		if(order.getCurrItem() > 0) {
			this.tables.get(t).addOrder(order);
			this.wtrs.get(g).increseWorkAmount();
			this.wtrs.get(getWtr(name)).increaseSalary();
			this.tables.get(t).setIsReady(false);
			this.tables.get(t).setWaiter(name);
			
		}
	}
	
	public void addOrder(String name,int tableId, String items) {
		if (tableId >= this.tables.size()) {
			System.out.println("This table is either not in service now or "+name+" cannot be assigned this table!");
			return;
		}
		else if(this.tables.get(tableId).getWaiter().equalsIgnoreCase(name)) {
			String[] itemsSplitted = items.split(":|\\-");
			Order order = new Order();
			for(int i=0; i<itemsSplitted.length; i++ ) {
				
				String itemName = itemsSplitted[i];
				i++;
				int j = getItem(itemName);
				int amount = Integer.parseInt(itemsSplitted[i]);
				while(amount>0) {
					if(j==-1) {
						System.out.println("Unknown item Waffle");
					}
					else if(this.items.get(j).getAmount() == 0) {
						System.out.println("Sorry! No " + itemName + " in the stock!");
					}
					else {
						System.out.println("Item " + itemName + " added into order");
						this.items.get(j).decreaseAmount();
						order.addItem(itemName,getItemCost(itemName));
					}
					amount--;
				}
			}
			if(order.getCurrItem() > 0) {
				this.tables.get(tableId).addOrder(order);
				this.wtrs.get(getWtr(name)).increaseSalary();
			}
		}
		else {
			System.out.println("This table is either not in service now or "+name+" cannot be assigned this table!");
			return;
		}
	}

	public void checkOut(String waiterName, int tableId) {
		
		int j = getWtr(waiterName);
		if(j==-1) {
			System.out.println("There is no waiter named " + waiterName);
			return;
		}
		if(this.tables.get(tableId).getWaiter().equalsIgnoreCase(waiterName)) {
			ArrayList<String> foodNames = new ArrayList<String>();
			for(int i=0;i<this.tables.get(tableId).getCurrOrderNum();i++) {
				for(int k=0;k<this.tables.get(tableId).getOrder(i).getCurrItem();k++) {
					//System.out.println(this.tables.get(tableId).getOrder(i).getItem(k).getName());
					foodNames.add(this.tables.get(tableId).getOrder(i).getItem(k).getName());
				}
			}
			String lastOne = new String("hamsitavaYumukluSucurtaCuavara");
			float total = 0;
			for(int m=0;m<foodNames.size();m++) {
				if(lastOne.equalsIgnoreCase(foodNames.get(m))) {
					//continue;
				}
				else {
					float cost1 = this.items.get(getItem(foodNames.get(m))).getCost();
					int frequency = Collections.frequency(foodNames, foodNames.get(m));
					System.out.print(foodNames.get(m)+":\t");
					System.out.printf("%.3f",cost1);
					System.out.print(" (x "+(int)frequency + ") ");
					float itemcost = cost1*frequency;
					System.out.printf("%.3f $\n",itemcost);
					total += itemcost;
				}
				lastOne = foodNames.get(m);
			}
			System.out.printf("Total:\t%.3f $\n",total);
			this.tables.get(tableId).resetTable();
			this.wtrs.get(getWtr(waiterName)).checkout();
		}
		else {
			System.out.println("This table is either not in service now or "+waiterName+" cannot be assigned this table!");
		}
		
	}

	public void stockStatus() {
		int i=0;
		for(i=0; i < items.size(); i++) {
			System.out.println(items.get(i).getName()+":\t"+ items.get(i).getAmount());
		}
	}

	public void getOrderStatus() {
		int i=0;
		do {
			int curr = this.tables.get(i).getCurrOrderNum();
			System.out.println("Table: " + i + "\n\t" + curr + " order(s)");
			for(int j=0; j<curr; j++ ) {
				System.out.println("\t\t"+this.tables.get(i).getOrder(j).getCurrItem()+" item(s)");
			}
		} while (++i<this.tables.size());
	}

	public void getTableStatus() {
		for (int i=0; i < tables.size(); i++ ) {
			System.out.print("Table " + i + ": ");
			if(tables.get(i).getIsReady() == false) {
				System.out.println("Reserved (" + tables.get(i).getWaiter() + ")");
			}
			else {
				System.out.println("Free");
			}
		}
	}
	
	public void getEmployerSalary() {
		int index = 0;
		while(index<this.getCurrEmpNum()) {
			Employer emp = this.emps.get(index);
			float netSalary = emp.getSalary();
			netSalary += emp.getSalary()*emp.totalOrder/10;
			System.out.println("Salary for "+emp.getName() + ": "+netSalary);
			index++;
		}
	}
	
	public void getWaiterSalary() {
		int index = 0;
		while(index < this.getCurrWtrNum()) {
			Waiter wtr = this.wtrs.get(index);
			float netSalary = wtr.getSalary();
			netSalary += wtr.getSalary() * wtr.getTotalOrder() * 0.05;
			System.out.println("Salary for "+wtr.getName()+": "+netSalary);
			index++;
		}
	}
}
