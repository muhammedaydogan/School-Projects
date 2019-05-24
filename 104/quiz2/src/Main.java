//package bbm104quiz2;
import java.util.Scanner;
public class Main {

	public static void main(String[] args) {
		System.out.println("1-Add a new member");
        System.out.println("2-Display all members");
        System.out.println("3-Search");
        System.out.println("4-Exit");

        SportCenter sprt = new SportCenter();
        /*
         * System.out.println(sprt.getNum());
        
        Member deneme1 = new Member("ahmet","�ahin", 20, 60);

        sprt.addMember(deneme1);
        System.out.println(sprt.getNum());
        sprt.search("ahmet","�ahiynhn");*/
        String name,surname;
        int input=-1;
        Scanner scan = new Scanner(System.in);
        while (input != 4)
        {
            System.out.println("Enter your choose");
            input = scan.nextInt();
            switch (input)
            {
                case 1:
                    System.out.println("Enter name");
                    name = scan.next();
                    System.out.println("Enter surname");
                    surname = scan.next();
                    System.out.println("Enter weight");
                    float weight = scan.nextFloat();
                    System.out.println("Enter height");
                    float height = scan.nextFloat();
                    Member m = new Member(name, surname, weight, height);
                    sprt.addMember(m);
                    System.out.println();
                    continue;
                    
                case 2:
                	sprt.printAllMembers();
                	System.out.println();
                    continue;
                case 3:
                	System.out.println("Enter name");
                    name = scan.next();
                    System.out.println("Enter surname");
                    surname = scan.next();
                    sprt.search(name, surname);
                    System.out.println();
                	continue;
                case 4:
                	scan.close();
                    System.exit(1);
                default:
                	System.out.println("Try Again..");
                    continue;
            }
        }

	}

}
