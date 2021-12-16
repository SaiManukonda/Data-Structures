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
 * SchedulePlanInputFile name is passed through the command line as args[1]
 * Read from SchedulePlanInputFile with the format:
 * 1. One line containing a course ID
 * 2. c (int): number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * SchedulePlanOutputFile name is passed through the command line as args[2]
 * Output to SchedulePlanOutputFile with the format:
 * 1. One line containing an int c, the number of semesters required to take the course
 * 2. c lines, each with up to 3 space separated course ID's
 */
public class SchedulePlan {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.SchedulePlan <adjacency list INput file> <schedule plan INput file> <schedule plan OUTput file>");
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

        StdIn.setFile(args[1]);
        String target  = StdIn.readLine();
        n = Integer.parseInt(StdIn.readLine());
        Set<String> ClassesTaken = new HashSet<>();
        for(int i = 0; i < n; i++){
            addAllprereq(StdIn.readLine(), ClassesTaken, adj);
        }
        Set<String> needToBeTaken = new HashSet<>();
        addAllprereq(target, needToBeTaken, adj);

        ArrayList<ArrayList<String>> classes = new ArrayList<ArrayList<String>>();
        while(!getEligible(ClassesTaken,adj).contains(target)){
            Boolean added = false;
            ArrayList<String> currSem = new ArrayList<String>();
            for (String i : needToBeTaken) {
                for (String f : getEligible(ClassesTaken,adj)) {
                    if(f.equals(i)){
                        currSem.add(f);
                        added = true;
                    }
                }
            }
            if(added){
                classes.add(currSem);
                for(int i = 0; i < currSem.size(); i++){
                    ClassesTaken.add(currSem.get(i));
                }
            }
        }

        StdOut.setFile(args[2]);
        StdOut.println(classes.size());
        for(int i  = 0; i < classes.size(); i++){
            for(int f = 0; f < classes.get(i).size(); f++){
                StdOut.print(classes.get(i).get(f) + " ");
            }
            StdOut.println();
        }
    }
    
    private static Set<String> getEligible(Set<String> ClassesTaken, ArrayList<ArrayList<String>> adj){
        Set<String> Eligible = new HashSet<>();
        for(int i = 0; i < adj.size(); i++){
            if(!ClassesTaken.contains(adj.get(i).get(0))){
                int f = 1;
                boolean temp = true;
                while(f < adj.get(i).size()){
                    if(!ClassesTaken.contains(adj.get(i).get(f))){
                        temp = false;
                    }
                    f++;
                }
                if(temp){
                    Eligible.add(adj.get(i).get(0));
                }
            }
        }
        return Eligible;
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
