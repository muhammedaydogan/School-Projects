
public class Main {

	public static void main(String[] args) {
		if(args.length < 1) {
			System.out.println("Commandline input format is wrong..");
			return;
		}
		Monopoly monopoly = new Monopoly("property.json","list.json");
		monopoly.play(args[0], "output.txt");
	}

}
