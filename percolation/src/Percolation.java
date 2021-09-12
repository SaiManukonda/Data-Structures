/**
 * 
 * Percolation
 * 
 * @author Ana Paula Centeno
 * @author Haolin (Daniel) Jin
 */

public class Percolation {

	private boolean[][] grid;          // gridSize by gridSize grid of sites; 
	                                   // true = open site, false = closed or blocked site
	private WeightedQuickUnionFind wquFind; // 
	private int 		gridSize;      // gridSize by gridSize is the size of the grid/system 
	private int         gridSquared;
	private int         virtualTop;    // virtual top    index on WeightedQuckUnionFind arrays
	private int         virtualBottom; // virtual bottom index on WeightedQuckUnionFind arrays

	/**
	* Constructor.
	* Initializes all instance variables
	*/
	public Percolation ( int n ){
		gridSize 	  = n;
		gridSquared   = gridSize * gridSize;
		wquFind       = new WeightedQuickUnionFind(gridSquared + 2);
		grid          = new boolean[gridSize][gridSize];   // every site is initialized to closed/blocked
		virtualTop    = gridSquared;
		virtualBottom = gridSquared + 1;
	} 

	/**
	* Getter method for GridSize 
	* @return integer representing the size of the grid.
	*/
	public int getGridSize () {
		return gridSize;
	}

	/**
	 * Returns the grid array
	 * @return grid array
	 */
	public boolean[][] getGridArray () {
		return grid;
	}

    /**
	 * Converts 2D coordinates into a flattened 1D array
	 * @param row grid row
	 * @param col grid column
     * @return int
	 */
    private int Converter(int row, int col){
        int temp = (row+1) * gridSize;
		int temp2 = gridSize - (col+1);
        int temp3 = (temp - temp2) - 1;
		return temp3;
	}
    
	/**
	 * Checks if a given coordinate is inside a 2D array or if it is out of bounds
	 * @param row grid row
	 * @param col grid column
     * @return int
	 */

	private boolean ValidCoordinates(int row, int col){
		if(row < 0 || col < 0){
			return false;
		}
		if(row >= gridSize || col >= gridSize){
			return false;
		}
		return true;

	}

	/**
	* Open the site at postion (x,y) on the grid to true and add an edge
	* to any open neighbor (left, right, top, bottom) and/or top/bottom virtual sites
	* Note: diagonal sites are not neighbors
	*
	* @param row grid row
	* @param col grid column
	* @return void
	*/
	
	public void openSite (int row, int col) {
		grid[row][col] = true;

		if(row == 0){
			wquFind.union(virtualTop, Converter(row, col));
		}
		if(row == gridSize-1){
			wquFind.union(virtualBottom, Converter(row, col));
		}

		int toprow = row - 1;
		int topcol = col;
		int bottomrow = row + 1;
		int bottomcol = col;
		int rightrow = row;
		int rightcol = col+1;
		int leftrow = row;
		int leftcol = col-1;

		if(ValidCoordinates(toprow ,topcol)){
             if(grid[toprow][topcol] == true){
				 wquFind.union(Converter(toprow,topcol), Converter(row, col));
			 }
		}
		if(ValidCoordinates(bottomrow ,bottomcol)){
			if(grid[bottomrow][bottomcol] == true){
				wquFind.union(Converter(bottomrow,bottomcol), Converter(row, col));
			}
		}
		if(ValidCoordinates(rightrow ,rightcol)){
			if(grid[rightrow][rightcol] == true){
				wquFind.union(Converter(rightrow,rightcol), Converter(row, col));
			}
		}
		if(ValidCoordinates(leftrow ,leftcol)){
			if(grid[leftrow][leftcol] == true){
				wquFind.union(Converter(leftrow,leftcol), Converter(row, col));
			}
		}
	}

	/**
	* Check if the system percolates (any top and bottom sites are connected by open sites)
	* @return true if system percolates, false otherwise
	*/
	public boolean percolationCheck () {
		return wquFind.find(virtualBottom) == wquFind.find(virtualTop);
	}

	/**
	 * Iterates over the grid array openning every site. 
	 * Starts at [0][0] and moves row wise 
	 * @param probability
	 * @param seed
	 */
	public void openAllSites (double probability, long seed) {

		// Setting the same seed before generating random numbers ensure that
		// the same numbers are generated between different runs
		StdRandom.setSeed(seed); // DO NOT remove this line

		// WRITE YOUR CODE HERE, DO NOT remove the line above
		for(int i = 0; i < gridSize; i++){
           for(int j =0; j < gridSize; j++){
			   if(probability > StdRandom.uniform()){
				   openSite(i, j);
			   }
		   }
		}
	}

	/**
	* Open up a new window and display the current grid using StdDraw library.
	* The output will be colored based on the grid array. Blue for open site, black for closed site.
	* @return: void 
	*/
	public void displayGrid () {
		double blockSize = 0.9 / gridSize;
		double zeroPt =  0.05+(blockSize/2), x = zeroPt, y = zeroPt;

		for ( int i = gridSize-1; i >= 0; i-- ) {
			x = zeroPt;
			for ( int j = 0; j < gridSize; j++) {
				if ( grid[i][j] ) {
					StdDraw.setPenColor( StdDraw.BOOK_LIGHT_BLUE );
					StdDraw.filledSquare( x, y ,blockSize/2);
					StdDraw.setPenColor( StdDraw.BLACK);
					StdDraw.square( x, y ,blockSize/2);		
				} else {
					StdDraw.filledSquare( x, y ,blockSize/2);
				}
				x += blockSize; 
			}
			y += blockSize;
		}
	}

	/**
	* Main method, for testing only, feel free to change it.
	*/
	public static void main ( String[] args ) {

		double p = 0.3;
		Percolation pl = new Percolation(5);

		/* 
		 * Setting a seed before generating random numbers ensure that
		 * the same numbers are generated between runs.
		 *
		 * If you would like to reproduce Autolab's output, update
		 * the seed variable to the value Autolab has used.
		 */
		long seed = System.currentTimeMillis();
		pl.openAllSites(p, seed);

		System.out.println("The system percolates: " + pl.percolationCheck());
		pl.displayGrid();
	}
}