import java.util.ArrayList;

/**
 * This class contains methods which perform various operations on a layered linked
 * list to simulate transit
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class Transit {
	/**
	 * Makes a layered linked list representing the given arrays of train stations, bus
	 * stops, and walking locations. Each layer begins with a location of 0, even though
	 * the arrays don't contain the value 0.
	 * 
	 * @param trainStations Int array listing all the train stations
	 * @param busStops Int array listing all the bus stops
	 * @param locations Int array listing all the walking locations (always increments by 1)
	 * @return The zero node in the train layer of the final layered linked list
	 */
	public static TNode makeList(int[] trainStations, int[] busStops, int[] locations) {
		int f = 0;
		TNode StartTrain = new TNode(f);
		TNode curr = StartTrain;
		for(int i = 0; i < trainStations.length; i++){
               curr.next = new TNode(trainStations[i]);
			   curr = curr.next;
		}
		TNode StartBus = new TNode(f);
		StartTrain.down = StartBus;
		curr = StartBus;
		for(int i = 0; i < busStops.length; i++){
			curr.next = new TNode(busStops[i]);
			curr = curr.next;
	   }
		TNode StartWalk = new TNode(f);
		StartBus.down = StartWalk;
		curr = StartWalk;
		for(int i = 0; i < locations.length; i++){
			curr.next = new TNode(locations[i]);
			curr = curr.next;
	   }

	   TNode currTrain = StartTrain;
	   TNode currBus = StartBus;
	   while(currTrain != null){
             if(currTrain.location == currBus.location){
				 currTrain.down = currBus;
				 currTrain = currTrain.next;
				 currBus = currBus.next;
			 }
			 else{
				 currBus = currBus.next;
			 }
	   }
	   currBus = StartBus;
	   TNode currWalk = StartWalk;
	   while(currBus != null){
             if(currBus.location == currWalk.location){
				 currBus.down = currWalk;
				 currBus = currBus.next;
				 currWalk = currWalk.next;
			 }
			 else{
				 currWalk = currWalk.next;
			 }
	   }
		
		return StartTrain;
	}
	
	/**
	 * Modifies the given layered list to remove the given train station but NOT its associated
	 * bus stop or walking location. Do nothing if the train station doesn't exist
	 * 
	 * @param trainZero The zero node in the train layer of the given layered list
	 * @param station The location of the train station to remove
	 */
	public static void removeTrainStation(TNode trainZero, int station) {
		TNode curr = trainZero;
		while(curr.next != null){
			if(curr.next.location == station){
				if(curr.next.next != null){
					curr.next = curr.next.next;
					return;
				}
				else{
					curr.next = null;
					return;
				}
			}
			curr = curr.next;
		}
	}

	/**
	 * Modifies the given layered list to add a new bus stop at the specified location. Do nothing
	 * if there is no corresponding walking location.
	 * 
	 * @param trainZero The zero node in the train layer of the given layered list
	 * @param busStop The location of the bus stop to add
	 */
	public static void addBusStop(TNode trainZero, int busStop) {
		TNode curr = trainZero;
		boolean action = false;
		while(curr.next != null){
			if(curr.next.location > busStop){
				curr = curr.down;
				action = true;
				break;
			}
			curr = curr.next;
		}
		if(action == false){
			curr = curr.down;
		}
		TNode next = curr.next;
		TNode newOne = new TNode(busStop);
		while(next != null){
			if(curr.location == busStop){
				return;
			}
             if(next.location > busStop){
                 curr.next = newOne;
                 curr.next.next = next;
				 break;
			 }
			 else{
				 curr = curr.next;
				 next = next.next;
			 }
		}
		TNode temp = curr;
		action = false;
		curr = curr.down;
        while(curr != null){
			if(curr.location == busStop){
                  newOne.down = curr;
				  action = true;
				  break;
			}
			curr = curr.next;
		}
		if(action == true){
			temp.next = newOne;
		}


	}
	
	/**
	 * Determines the optimal path to get to a given destination in the walking layer, and 
	 * collects all the nodes which are visited in this path into an arraylist. 
	 * 
	 * @param trainZero The zero node in the train layer of the given layered list
	 * @param destination An int representing the destination
	 * @return
	 */
	public static ArrayList<TNode> bestPath(TNode trainZero, int destination) {
		ArrayList<TNode> path = new ArrayList<TNode>();
		TNode currNode = trainZero;
		path.add(currNode);
		while(currNode != null){
             if(currNode.location == destination){
				 break;
			 }
			 else if(currNode.next != null){
				 if(currNode.next.location > destination){
					 currNode = currNode.down;
					 path.add(currNode);
				 }
				 else if(currNode.next.location <= destination){
					 currNode = currNode.next;
					 path.add(currNode);
				 }
			 }
			 else{
				 currNode = currNode.down;
				 path.add(currNode);
			 }
		}
		while(currNode.down != null){
           currNode = currNode.down;
		   path.add(currNode);
		}
		return path;
	}

	/**
	 * Returns a deep copy of the given layered list, which contains exactly the same
	 * locations and connections, but every node is a NEW node.
	 * 
	 * @param trainZero The zero node in the train layer of the given layered list
	 * @return
	 */
	public static TNode duplicate(TNode trainZero) {
		TNode duplicateTrain = new TNode(0);
		TNode duplicateBus = new TNode(0);
		duplicateTrain.down = duplicateBus;
		TNode duplicateWalk = new TNode(0);
		duplicateBus.down = duplicateWalk;

		TNode currNode = trainZero.next;
		TNode currDuplicateTrain = duplicateTrain;

		while(currNode != null){
             currDuplicateTrain.next = new TNode(currNode.location);
			 if(currNode.down != null){
				 currDuplicateTrain.next.down = new TNode(currNode.down.location);
			 }
			 currNode = currNode.next;
			 currDuplicateTrain = currDuplicateTrain.next;
		}

		currNode = trainZero.down.next;
		TNode currDuplicateBus = duplicateBus;
		while(currNode != null){
			currDuplicateBus.next = new TNode(currNode.location);
			if(currNode.down != null){
				currDuplicateBus.next.down = new TNode(currNode.down.location);
			}
			currNode = currNode.next;
			currDuplicateBus = currDuplicateBus.next;
		}

		currNode = trainZero.down.down.next;
		TNode currDuplicateWalk = duplicateWalk;
		while(currNode != null){
			currDuplicateWalk.next = new TNode(currNode.location);
			currNode = currNode.next;
			currDuplicateWalk = currDuplicateWalk.next;
		}

		return duplicateTrain;

	}

	/**
	 * Modifies the given layered list to add a scooter layer in between the bus and
	 * walking layer.
	 * 
	 * @param trainZero The zero node in the train layer of the given layered list
	 * @param scooterStops An int array representing where the scooter stops are located
	 */
	public static void addScooter(TNode trainZero, int[] scooterStops) {
		TNode ScooterZero = new TNode(0);
		TNode BusZero = trainZero.down;
		TNode WalkZero = BusZero.down;

		TNode curr = ScooterZero;
		for(int i = 0; i < scooterStops.length; i++){
			curr.next = new TNode(scooterStops[i]);
			curr = curr.next;
		}

		TNode currBus = BusZero;
		TNode currScooter = ScooterZero;
		while(currBus != null){
			if(currBus.location == currScooter.location){
				currBus.down = currScooter;
				currBus = currBus.next;
				currScooter = currScooter.next;
			}
			else{
				currScooter = currScooter.next;
			}
	  }

	    currScooter = ScooterZero;
		TNode currWalk = WalkZero;
		while(currScooter != null){
			if(currScooter.location == currWalk.location){
				currScooter.down = currWalk;
				currScooter = currScooter.next;
				currWalk = currWalk.next;
			}
			else{
				currWalk= currWalk.next;
			}
	  }
	}
}