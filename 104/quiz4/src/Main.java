/*
#########################################################################################
# -This is a template source code file given you to complete 4th quiz work.             #
#                                                                                       #
# -You are strongly expected to declare and define data structure and recursive         #
# method within the comment-lines dedicated below.                                      #
#                                                                                       #
# -DO-NOT modify the comment-lines (not even a letter in those lines) that determine    #
# the boundaries of the blocks in which you are to perform the expected behaviors.      #
# In the case you modify you are likely to get 0 points from this quiz!                 #
#                                                                                       #
# -You can expand the lines of these blocks as much as you wish.                        #
#                                                                                       #
# -DO-NOT rename this file!                                                             #
# -DO-NOT use any class file other than this!                                           #
# -Feel free to define functions as much as you wish but within this class!             #
#########################################################################################
*/
public class Main{

    public static void main(String [ ] args) {
        // Declare all the data structure(s) below that you need
        // DO-NOT spoil the inside of the block with the code-snippets other than declarations!
        /////////////// DATA-STRUCTURE DECLARATION: BEGIN ///////////////

        if(args.length < 1) {
    		System.out.println("please give inputfile as parameter..");
    		return;
    	}
    	HashMap<String, String> keys = new HashMap<String, String>();
    	try(BufferedReader inpFile = new BufferedReader(new FileReader(args[0]))){
    		String line;
    		while((line = inpFile.readLine()) != null) {
    			String[] splittedLine = line.split("->",2);
    			keys.put(splittedLine[0], splittedLine[1]);
    		}
    	} catch (Exception e) {
			System.out.println("File i/o exception: " + args[0]);
		}
    	System.out.println( recursion("S", keys) );

        /////////////// DATA-STRUCTURE DECLARATION: END   ///////////////

    }
    
    
    // Define and fill recursive method below
    // Note: The whole structure of the recursion (including the begin-end curly braces)
    // should be placed within the block
    /////////////// RECURSION: BEGIN ///////////////

    public static String recursion(String letter, HashMap<String, String> map) {
    	if(map.get(letter) == null) {
    		return letter;
    	}
    	String result = map.get(letter);
    	int i = 0;
    	while(i<result.length()) {
    		String subLetter = String.valueOf(result.charAt(i));
    		result = result.replace(subLetter, recursion(subLetter, map));
    		i++;	
    	}
    	return "(" + result + ")";
    }

    /////////////// RECURSION: END   ///////////////
}
