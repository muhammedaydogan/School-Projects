
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
//import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;

public class Assignment2 {

	public static void main(String[] args) {
		
		Restaurant mado = new Restaurant();
		
		try(BufferedReader br = new BufferedReader(new FileReader("setup.dat"))) {
			String line;
			
			// / / / / / / / / / / / / / / / / / / / / /  R e a d i n g   s e t u p . d a t
			while((line = br.readLine()) != null) {
				String[] cmnd = line.split(" ", 2);
				if (cmnd[0].equalsIgnoreCase("add_item")) {
					String[] attr = cmnd[1].split(";", 4);
					Item item = new Item( attr[0], Float.parseFloat(attr[1]), Integer.parseInt(attr[2]) );
					mado.addItem(item);
				}
				else if (cmnd[0].equalsIgnoreCase("add_employer")) {
					String[] attr = cmnd[1].split(";", 2);
					Employer emp = new Employer(attr[0], Float.parseFloat(attr[1]));
					mado.addEmployer(emp);
				}
				else if (cmnd[0].equalsIgnoreCase("add_waiter")) {
					String[] attr = cmnd[1].split(";", 2);
					Waiter wtr = new Waiter(attr[0], Float.parseFloat(attr[1]));
					mado.addWaiter(wtr);
				}
			}
			br.close();
			// / / / / / / / / / / / / / / / / / / / / /  R e a d i n g   c o m m a n d s . d a t
			
			
			BufferedReader br2 = new BufferedReader(new FileReader("commands.dat"));
			while((line = br2.readLine()) != null) {
				String[] cmnd = line.split(" ", 2);
				System.out.println("***********************************\nPROGRESSING COMMAND: "+cmnd[0]);
				if (cmnd[0].equalsIgnoreCase("create_table")) {
					String[] attr = cmnd[1].split(";", 2);
					Table tbl = new Table(Integer.parseInt(attr[1]), attr[0]);
					mado.createTable(tbl);
				}
				else if (cmnd[0].equalsIgnoreCase("new_order")) {
					String[] attr = cmnd[1].split(";", 3);
					int i = mado.getWtr(attr[0]);
					mado.newOrder(attr[0], Integer.parseInt(attr[1]), attr[2]);
				}
				else if (cmnd[0].equalsIgnoreCase("add_order")) {
					String[] attr = cmnd[1].split(";", 3);
					mado.addOrder(attr[0], Integer.parseInt(attr[1]), attr[2]);
				}
				else if (cmnd[0].equalsIgnoreCase("check_out")) {
					String[] attr = cmnd[1].split(";", 2);
					mado.checkOut(attr[0], Integer.parseInt(attr[1]));
				}
				else if (cmnd[0].equalsIgnoreCase("stock_status")) {
					mado.stockStatus();
				}
				else if (cmnd[0].equalsIgnoreCase("get_order_status")) {
					mado.getOrderStatus();
				}
				else if (cmnd[0].equalsIgnoreCase("get_table_status")) {
					mado.getTableStatus();
				}
				else if (cmnd[0].equalsIgnoreCase("get_employer_salary")) {
					mado.getEmployerSalary();
				}
				else if (cmnd[0].equalsIgnoreCase("get_waiter_salary")) {
					mado.getWaiterSalary();
				}
				
			}
			br2.close();
		}
		catch(IOException e) {
			System.out.println("Cannot open File");
		}
	}

}
