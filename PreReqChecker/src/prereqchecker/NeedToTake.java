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
 * NeedToTakeInputFile name is passed through the command line as args[1]
 * Read from NeedToTakeInputFile with the format:
 * 1. One line, containing a course ID
 * 2. c (int): Number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * NeedToTakeOutputFile name is passed through the command line as args[2]
 * Output to NeedToTakeOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class NeedToTake {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java NeedToTake <adjacency list INput file> <need to take INput file> <need to take OUTput file>");
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
        Set<String> ClassesTaken = new HashSet<>();
        StdIn.setFile(args[1]);
        String target  = StdIn.readLine();
        Set<String> targetPreReqs = new HashSet<>();
        addAllprereq(target, targetPreReqs, adj);
        targetPreReqs.remove(target);
        n = Integer.parseInt(StdIn.readLine());
        for(int i = 0; i < n; i++){
            addAllprereq(StdIn.readLine(), ClassesTaken, adj);
        }
        StdOut.setFile(args[2]);
        for (String i : targetPreReqs) {
            if(!ClassesTaken.contains(i)){
                StdOut.println(i);
            }
        }
    }
    private static void addAllprereq(String course, Set<String> ClassesTaken, ArrayList<ArrayList<String>> adj){
        if(adj.get(find(course,adj)).size() == 1){
            if(!ClassesTaken.contains(course)){
              ClassesTaken.add(course);
            }
        }
        else{
          if(!ClassesTaken.contains(course)){
              ClassesTaken.add(course);
            }
            for(int i = 1;i < adj.get(find(course,adj)).size(); i++){
              addAllprereq(adj.get(find(course,adj)).get(i), ClassesTaken, adj);
            }
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
