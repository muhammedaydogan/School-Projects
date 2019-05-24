import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
// Please run application with these commands:
// javac Main.java
// java Main fixtures.txt football.out basketball.out volleyball.out
public class Main {

	public static void main(String[] args) {

		if (args.length != 4) {
			System.out.println("Error: Wrong commandline input format");
			return;
		}
		try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {

			String line;
			ArrayList<Sport> fTable = new ArrayList<Sport>();
			ArrayList<Sport> vTable = new ArrayList<Sport>();
			ArrayList<Sport> bTable = new ArrayList<Sport>();

			while ((line = br.readLine()) != null) {

				String[] words = line.split("\t");
				String type = words[0], team1 = words[1], team2 = words[2];
				team1 = team1.trim();
				team2 = team2.trim();
				// System.out.println("<"+team2+">");
				String[] scoreArr = words[3].split(" ");
				int[] scores = new int[2];
				scores[0] = Integer.parseInt(scoreArr[0]);
				scores[1] = Integer.parseInt(scoreArr[2]);

				if (type.equals("F")) {
					if (getTeamId(fTable, team1) == -1) {
						fTable.add(new Football(team1));
					}
					if (getTeamId(fTable, team2) == -1) {
						fTable.add(new Football(team2));
					}
					if (scores[0] > scores[1]) {
						fTable.get(getTeamId(fTable, team1)).win(scores[0], scores[1]);
						fTable.get(getTeamId(fTable, team2)).lose(scores[1], scores[0]);

					} else if (scores[0] < scores[1]) {
						fTable.get(getTeamId(fTable, team1)).lose(scores[0], scores[1]);
						fTable.get(getTeamId(fTable, team2)).win(scores[1], scores[0]);

					} else if (scores[0] == scores[1]) {
						fTable.get(getTeamId(fTable, team1)).draw(scores[0], scores[1]);
						fTable.get(getTeamId(fTable, team2)).draw(scores[1], scores[0]);
					}
				} else if (type.equals("V")) {
					if (getTeamId(vTable, team1) == -1) {
						vTable.add(new Volleyball(team1));
					}
					if (getTeamId(vTable, team2) == -1) {
						vTable.add(new Volleyball(team2));
					}
					if (scores[0] > scores[1]) {
						vTable.get(getTeamId(vTable, team1)).win(scores[0], scores[1]);
						vTable.get(getTeamId(vTable, team2)).lose(scores[1], scores[0]);

					} else if (scores[0] < scores[1]) {
						vTable.get(getTeamId(vTable, team1)).lose(scores[0], scores[1]);
						vTable.get(getTeamId(vTable, team2)).win(scores[1], scores[0]);

					} else if (scores[0] == scores[1]) {
						vTable.get(getTeamId(vTable, team1)).draw(scores[0], scores[1]);
						vTable.get(getTeamId(vTable, team2)).draw(scores[1], scores[0]);
					}
				} else if (type.equals("B")) {
					if (getTeamId(bTable, team1) == -1) {
						bTable.add(new Basketball(team1));
					}
					if (getTeamId(bTable, team2) == -1) {
						bTable.add(new Basketball(team2));
					}
					if (scores[0] > scores[1]) {
						bTable.get(getTeamId(bTable, team1)).win(scores[0], scores[1]);
						bTable.get(getTeamId(bTable, team2)).lose(scores[1], scores[0]);

					} else if (scores[0] < scores[1]) {
						bTable.get(getTeamId(bTable, team1)).lose(scores[0], scores[1]);
						bTable.get(getTeamId(bTable, team2)).win(scores[1], scores[0]);

					} else if (scores[0] == scores[1]) {
						bTable.get(getTeamId(bTable, team1)).draw(scores[0], scores[1]);
						bTable.get(getTeamId(bTable, team2)).draw(scores[1], scores[0]);
					}
				}
			}
			br.close();
			for (int i = 0; i < fTable.size(); i++) {
				int maxPts = 0, maxGoalDiff = 0, maxIndex = 0;
				for (int j = i; j < fTable.size(); j++) {
					if (fTable.get(j).getPts() > maxPts) {
						maxPts = fTable.get(j).getPts();
						maxGoalDiff = fTable.get(j).getGoal() - fTable.get(j).getGoalAgainst();
						maxIndex = j;
					} else if (fTable.get(j).getPts() == maxPts) {
						if (fTable.get(j).getGoal() - fTable.get(j).getGoalAgainst() > maxGoalDiff) {
							maxGoalDiff = fTable.get(j).getGoal() - fTable.get(j).getGoalAgainst();
							maxIndex = j;
						}
					}
				}
				Sport temp = fTable.get(maxIndex);
				fTable.remove(maxIndex);
				fTable.add(i, temp);

			}
			
			OutputFile fOut = new OutputFile(args[1]);
			for (int i = 0; i < fTable.size(); i++) {
				/*System.out.print(i + 1 + ".\t" + fTable.get(i).getName() + "\t" + fTable.get(i).getTotal() + "\t"
						+ fTable.get(i).getWon() + "\t" + fTable.get(i).getDraw() + "\t" + fTable.get(i).getLoss()
						+ "\t" + fTable.get(i).getGoal() + ":" + fTable.get(i).getGoalAgainst() + "\t"
						+ fTable.get(i).getPts());*/
				fOut.write(i+1);
				fOut.write(".\t");
				fOut.write(fTable.get(i).getName());
				fOut.write("\t");
				fOut.write(fTable.get(i).getTotal());
				fOut.write("\t");
				fOut.write(fTable.get(i).getWon());
				fOut.write("\t");
				fOut.write(fTable.get(i).getDraw());
				fOut.write("\t");
				fOut.write(fTable.get(i).getLoss());
				fOut.write("\t");
				fOut.write(fTable.get(i).getGoal());
				fOut.write(":");
				fOut.write(fTable.get(i).getGoalAgainst());
				fOut.write("\t");
				fOut.write(fTable.get(i).getPts());

				if(i != fTable.size()-1) {
					//System.out.println();
					fOut.write("\n");
				}
			}
			/////////////////
			for (int i = 0; i < vTable.size(); i++) {
				int maxPts = 0, maxGoalDiff = 0, maxIndex = 0;
				for (int j = i; j < vTable.size(); j++) {
					if (vTable.get(j).getPts() > maxPts) {
						maxPts = vTable.get(j).getPts();
						maxGoalDiff = vTable.get(j).getGoal() - vTable.get(j).getGoalAgainst();
						maxIndex = j;
					} else if (vTable.get(j).getPts() == maxPts) {
						if (vTable.get(j).getGoal() - vTable.get(j).getGoalAgainst() > maxGoalDiff) {
							maxGoalDiff = vTable.get(j).getGoal() - vTable.get(j).getGoalAgainst();
							maxIndex = j;
						}
					}
				}
				Sport temp = vTable.get(maxIndex);
				vTable.add(i, temp);
				vTable.remove(maxIndex+1);
				
			}
			
			OutputFile vOut = new OutputFile(args[3]);
			for (int i = 0; i < vTable.size(); i++) {
				vOut.write(i+1);
				vOut.write(".\t");
				vOut.write(vTable.get(i).getName());
				vOut.write("\t");
				vOut.write(vTable.get(i).getTotal());
				vOut.write("\t");
				vOut.write(vTable.get(i).getWon());
				vOut.write("\t");
				vOut.write(vTable.get(i).getLoss());
				vOut.write("\t");
				vOut.write(vTable.get(i).getGoal());
				vOut.write(":");
				vOut.write(vTable.get(i).getGoalAgainst());
				vOut.write("\t");
				vOut.write(vTable.get(i).getPts());
				

				if(i != vTable.size()-1) {
					vOut.write("\n");
				}
			}
			/////////////
			for (int i = 0; i < bTable.size(); i++) {
				int maxPts = 0, maxGoalDiff = 0, maxIndex = 0;
				for (int j = i; j < bTable.size(); j++) {
					if (bTable.get(j).getPts() > maxPts) {
						maxPts = bTable.get(j).getPts();
						maxGoalDiff = bTable.get(j).getGoal() - bTable.get(j).getGoalAgainst();
						maxIndex = j;
					} else if (bTable.get(j).getPts() == maxPts) {
						if (bTable.get(j).getGoal() - bTable.get(j).getGoalAgainst() > maxGoalDiff) {
							maxGoalDiff = bTable.get(j).getGoal() - bTable.get(j).getGoalAgainst();
							maxIndex = j;
						}
					}
				}
				Sport temp = bTable.get(maxIndex);
				bTable.add(i, temp);
				bTable.remove(maxIndex+1);
				
			}
			
			OutputFile bOut = new OutputFile(args[2]);
			for (int i = 0; i < bTable.size(); i++) {
				/*System.out.print(i + 1 + ".\t" + bTable.get(i).getName() + "\t" + bTable.get(i).getTotal() + "\t"
						+ bTable.get(i).getWon() + "\t" + bTable.get(i).getLoss()
						+ "\t" + bTable.get(i).getGoal() + ":" + bTable.get(i).getGoalAgainst() + "\t"
						+ bTable.get(i).getPts());*/
				bOut.write(i+1);
				bOut.write(".\t");
				bOut.write(bTable.get(i).getName());
				bOut.write("\t");
				bOut.write(bTable.get(i).getTotal());
				bOut.write("\t");
				bOut.write(bTable.get(i).getWon());
				bOut.write("\t");
				bOut.write(bTable.get(i).getLoss());
				bOut.write("\t");
				bOut.write(bTable.get(i).getGoal());
				bOut.write(":");
				bOut.write(bTable.get(i).getGoalAgainst());
				bOut.write("\t");
				bOut.write(bTable.get(i).getPts());

				if(i != bTable.size()-1) {
					//System.out.println();
					bOut.write("\n");
				}
			}
		} catch (IOException e) {
			System.out.println("Cannot open file");
			return;
		}
	}

	public static int getTeamId(ArrayList<Sport> table, String teamName) {
		int j = -1;
		for (int i = 0; i < table.size(); i++) {
			if (table.get(i).getName().equals(teamName)) {
				j = i;
				break;
			}
		}
		return j;
	}
}

class Sport {

	private String name;
	private int total, won, loss, goal, goalAgainst, pts;

	public Sport(String name) {
		this.name = name;
		this.total = 0;
		this.won = 0;
		this.loss = 0;
		this.goal = 0;
		this.goalAgainst = 0;
		this.pts = 0;
	}

	public int getTotal() {
		return total;
	}

	public void increaseTotal() {
		this.total++;
	}

	public int getWon() {
		return won;
	}

	public void increaseWon() {
		this.won++;
	}

	public int getLoss() {
		return loss;
	}

	public void increaseLoss() {
		this.loss++;
	}

	public int getGoal() {
		return goal;
	}

	public void increaseGoal(int goal) {
		this.goal += goal;
	}

	public int getGoalAgainst() {
		return goalAgainst;
	}

	public void increaseGoalAgainst(int goalAgainst) {
		this.goalAgainst += goalAgainst;
	}

	public int getPts() {
		return pts;
	}

	public void increasePts(int pts) {
		this.pts += pts;
	}

	public String getName() {
		return name;
	}

	public void win(int goal, int goalAgainst) {
		System.out.println("error no:5");
	}

	public void lose(int goal, int goalAgainst) {
		System.out.println("error no:6");
	}

	public void draw(int goal, int goalAgainst) {
		System.out.println("error no:7");
	}

	public int getDraw() {
		System.out.println("error no:4");
		return -1;
	}

}

class Football extends Sport {
	private int draw;

	public Football(String name) {
		super(name);
		this.draw = 0;
	}

	public int getDraw() {
		return this.draw;
	}

	public void increaseDraw() {
		this.draw++;
	}

	public void win(int goal, int goalAgainst) {
		increaseGoal(goal);
		increaseGoalAgainst(goalAgainst);
		increaseWon();
		increaseTotal();
		increasePts(3);
	}

	public void lose(int goal, int goalAgainst) {
		increaseGoal(goal);
		increaseGoalAgainst(goalAgainst);
		increaseLoss();
		increaseTotal();
	}

	public void draw(int goal, int goalAgainst) {
		increaseGoal(goal);
		increaseGoalAgainst(goalAgainst);
		increaseDraw();
		increaseTotal();
		increasePts(1);
	}
}

class Volleyball extends Sport {
	public Volleyball(String name) {
		super(name);
	}

	public void lose(int goal, int goalAgainst) {
		increaseGoal(goal);
		increaseGoalAgainst(goalAgainst);
		increaseLoss();
		increaseTotal();
		if (goal == 2) {
			increasePts(1);
		}
	}

	public void win(int goal, int goalAgainst) {
		increaseGoal(goal);
		increaseGoalAgainst(goalAgainst);
		increaseWon();
		increaseTotal();
		if (goalAgainst == 2) {
			increasePts(2);
		} else if (goalAgainst == 0 || goalAgainst == 1) {
			increasePts(3);
		}
		else {
			System.out.println("error no:0");
		}
	}

	public void draw(int goal, int goalAgainst) {
		System.out.println("error: a volleyball match cannot be drawed");
	}
}

class Basketball extends Sport {
	public Basketball(String name) {
		super(name);
	}

	public void lose(int goal, int goalAgainst) {
		increaseGoal(goal);
		increaseGoalAgainst(goalAgainst);
		increaseLoss();
		increaseTotal();
		increasePts(1);
	}

	public void win(int goal, int goalAgainst) {
		increaseGoal(goal);
		increaseGoalAgainst(goalAgainst);
		increaseWon();
		increaseTotal();
		increasePts(2);
	}

	public void draw(int goal, int goalAgainst) {
		increaseGoal(goal);
		increaseGoalAgainst(goalAgainst);
		increaseTotal();
		// increasePts(goal-goalAgainst);
		// ???????????????????????????????????????????????
	}
}

class OutputFile {
	private File file;
	private FileWriter fr;
	private BufferedWriter br;
	private PrintWriter pr;
	public OutputFile(String fileName) {
		this.file = new File(fileName);
		try {
			file.createNewFile();
			this.fr = new FileWriter(file,false);
			br = new BufferedWriter(fr);
			pr = new PrintWriter(br,false);
			pr.flush();
			pr.close();
			br.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void write(String data) {

		try{
			this.fr = new FileWriter(file,true);
			br = new BufferedWriter(fr);
			pr = new PrintWriter(br);
			pr.print(data);
		}
		catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				pr.close();
				br.close();
				fr.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void write(int data) {

		try{
			this.fr = new FileWriter(file,true);
			br = new BufferedWriter(fr);
			pr = new PrintWriter(br);
			pr.print(data);
		}
		catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				pr.close();
				br.close();
				fr.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}