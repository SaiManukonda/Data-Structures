package prereqchecker;
import java.util.*;

/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * AdjListInputFile name is passed through the command line as args[0]
 * Read from AdjListInputFile with the format:
 * 1. a (int): number of courses in the graph
 * 2. a lines, each with 1 course ID
 * 3. b (int): number of edges in the graph
 * 4. b lines, each with a source ID
 * 
 * Step 2:
 * AdjListOutputFile name is passed through the command line as args[1]
 * Output to AdjListOutputFile with the format:
 * 1. c lines, each starting with a different course ID, then 
 *    listing all of that course's prerequisites (space separated)
 */
public class AdjList {
    public static void main(String[] args) {

        if ( args.length < 2 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.AdjList <adjacency list INput file> <adjacency list OUTput file>");
            return;
        }
        
	    StdIn.setFile(args[0]);
        int n = Integer.parseInt(StdIn.readLine());
        ArrayList<ArrayList<String>> adj = new ArrayList<ArrayList<String>>();
        for(int i = 0; i < n ; i++){
            ArrayList<String> temp = new ArrayList<String>();
            temp.add(StdIn.readLine());
            adj.add(temp);
        }
        n = Integer.parseInt(StdIn.readLine());
        for(int i = 0; i < n; i++){
            adj.get(find(StdIn.readString(),adj)).add(StdIn.readString());
        }
        StdOut.setFile(args[1]);
        for(int i = 0; i < adj.size(); i++){
            ArrayList<String> curr = adj.get(i);
            for(int j = 0; j < curr.size(); j++){
                StdOut.print(curr.get(j) + " ");
            }
            StdOut.println();
        }
        
    }

    private static int find(String course, ArrayList<ArrayList<String>> adj){
        for(int i = 0; i < adj.size(); i++){
            if(adj.get(i).get(0).equals(course)){
                return i;
            }
        }
        return -1;
    }
}
