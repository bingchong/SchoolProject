import org.omg.CORBA.Current;
//Name: Bing Chong Lim				Student ID: 22789609
//Project 1							Class Account: cs61b-gw
/* RunLengthEncoding.java */

/**
 *  The RunLengthEncoding class defines an object that run-length encodes an
 *  Ocean object.  Descriptions of the methods you must implement appear below.
 *  They include constructors of the form
 *
 *      public RunLengthEncoding(int i, int j, int starveTime);
 *      public RunLengthEncoding(int i, int j, int starveTime,
 *                               int[] runTypes, int[] runLengths) {
 *      public RunLengthEncoding(Ocean ocean) {
 *
 *  that create a run-length encoding of an Ocean having width i and height j,
 *  in which sharks starve after starveTime timesteps.
 *
 *  The first constructor creates a run-length encoding of an Ocean in which
 *  every cell is empty.  The second constructor creates a run-length encoding
 *  for which the runs are provided as parameters.  The third constructor
 *  converts an Ocean object into a run-length encoding of that object.
 *
 *  See the README file accompanying this project for additional details.
 */

public class RunLengthEncoding {

  /**
   *  Define any variables associated with a RunLengthEncoding object here.
   *  These variables MUST be private.
   */
	
	private DList list = new DList();
	private DListNode curr = list.head;
	private int width, height, hunger;



  /**
   *  The following methods are required for Part II.
   */

  /**
   *  RunLengthEncoding() (with three parameters) is a constructor that creates
   *  a run-length encoding of an empty ocean having width i and height j,
   *  in which sharks starve after starveTime timesteps.
   *  @param i is the width of the ocean.
   *  @param j is the height of the ocean.
   *  @param starveTime is the number of timesteps sharks survive without food.
   */

  public RunLengthEncoding(int i, int j, int starveTime) {
    // Your solution here.
	this(i,j,starveTime,null,null);  
	  
  }

  /**
   *  RunLengthEncoding() (with five parameters) is a constructor that creates
   *  a run-length encoding of an ocean having width i and height j, in which
   *  sharks starve after starveTime timesteps.  The runs of the run-length
   *  encoding are taken from two input arrays.  Run i has length runLengths[i]
   *  and species runTypes[i].
   *  @param i is the width of the ocean.
   *  @param j is the height of the ocean.
   *  @param starveTime is the number of timesteps sharks survive without food.
   *  @param runTypes is an array that represents the species represented by
   *         each run.  Each element of runTypes is Ocean.EMPTY, Ocean.FISH,
   *         or Ocean.SHARK.  Any run of sharks is treated as a run of newborn
   *         sharks (which are equivalent to sharks that have just eaten).
   *  @param runLengths is an array that represents the length of each run.
   *         The sum of all elements of the runLengths array should be i * j.
   */

  public RunLengthEncoding(int i, int j, int starveTime,
                           int[] runTypes, int[] runLengths) {
    // Your solution here.
	  buildDList(i, j, starveTime, runTypes, runLengths);
  }
  
  public void buildDList(int i, int j, int starveTime,
          						int[] runTypes, int[] runLengths){
	  width = i;
	  height = j;
	  hunger = starveTime;
	  curr = list.head;
	  //if it's an empty ocean, create 1 node that contains the size of the empty ocean
	  if (runTypes.length == 0 && runLengths.length == 0){
		  curr.next = new DListNode (Ocean.EMPTY, i*j, null, curr);
	  }
	  //condense the information given into a run-length encoding 
	  //create new node to store the species and the size, forming the DList 	  
	  for (int k = 0; k < runLengths.length; k++){
		  if(runTypes[k] == Ocean.EMPTY || runTypes[k] == Ocean.FISH){
			  curr.next = new DListNode(runTypes[k], runLengths[k], null, curr);
		  }
		  else{
			  curr.next = new DListNode(Ocean.SHARK, runLengths[k], runTypes[k]-3, null, curr);
		  }
		  curr = curr.next;
	  }

	  curr = list.head;
  }

  /**
   *  restartRuns() and nextRun() are two methods that work together to return
   *  all the runs in the run-length encoding, one by one.  Each time
   *  nextRun() is invoked, it returns a different run (represented as a
   *  TypeAndSize object), until every run has been returned.  The first time
   *  nextRun() is invoked, it returns the first run in the encoding, which
   *  contains cell (0, 0).  After every run has been returned, nextRun()
   *  returns null, which lets the calling program know that there are no more
   *  runs in the encoding.
   *
   *  The restartRuns() method resets the enumeration, so that nextRun() will
   *  once again enumerate all the runs as if nextRun() were being invoked for
   *  the first time.
   *
   *  (Note:  Don't worry about what might happen if nextRun() is interleaved
   *  with addFish() or addShark(); it won't happen.)
   */

  /**
   *  restartRuns() resets the enumeration as described above, so that
   *  nextRun() will enumerate all the runs from the beginning.
   */

  public void restartRuns() {
    // Your solution here.
	  curr = list.head;
  }

  /**
   *  nextRun() returns the next run in the enumeration, as described above.
   *  If the runs have been exhausted, it returns null.  The return value is
   *  a TypeAndSize object, which is nothing more than a way to return two
   *  integers at once.
   *  @return the next run in the enumeration, represented by a TypeAndSize
   *          object.
   */

  public TypeAndSize nextRun() {
    // Replace the following line with your solution.
	//if the list is empty or reaches the end of the list, return null
	curr = curr.next;
	if (curr == null){
		return null;
	}
	//return various TypeAndSize objects depending on the species
	else if (curr.getSpecies() == Ocean.FISH){						
    	return new TypeAndSize(Ocean.FISH, curr.getSize());
    }
    else if (curr.getSpecies() == Ocean.EMPTY){
    	return new TypeAndSize(Ocean.EMPTY, curr.getSize());
    }
    else
    	return new TypeAndSize(Ocean.SHARK, curr.getSize());
  }

  /**
   *  toOcean() converts a run-length encoding of an ocean into an Ocean
   *  object.  You will need to implement the three-parameter addShark method
   *  in the Ocean class for this method's use.
   *  @return the Ocean represented by a run-length encoding.
   */

  public Ocean toOcean() {
    // Replace the following line with your solution.
	curr = list.head;
	Ocean newOcean = new Ocean(width,height,hunger);
	//check for species in the node and put the respective species into the respective cells of the array
	//decreasing the size2 each time it places a species into the cell (size2 is a replica of the real size. size2 is used so that the original size does not get modified)
	if(curr!=null && curr.next!=null){
	for(int x = 0; x < height; x++){
		for(int y = 0; y < width; y++){
				if(curr.next.getSpecies()==Ocean.EMPTY && curr.next.getSize2() > 0){
					curr.next.setSize2(curr.next.getSize2()-1);
					}
				else if(curr.next.getSpecies()==Ocean.FISH && curr.next.getSize2() > 0){
					newOcean.addFish(y, x);
					curr.next.setSize2(curr.next.getSize2()-1);
				}
				else if(curr.next.getSpecies()==Ocean.SHARK && curr.next.getSize2() > 0){
					newOcean.addShark(y, x, curr.next.getHunger());
					curr.next.setSize2(curr.next.getSize2()-1);
				}
				if (curr.next!= null && curr.next.getSize2() == 0){
					curr = curr.next;
				}
		}
	}
	}
	curr = list.head;
    return newOcean;
  }

  /**
   *  The following method is required for Part III.
   */

  /**
   *  RunLengthEncoding() (with one parameter) is a constructor that creates
   *  a run-length encoding of an input Ocean.  You will need to implement
   *  the sharkFeeding method in the Ocean class for this constructor's use.
   *  @param sea is the ocean to encode.
   */

  public RunLengthEncoding(Ocean sea) {
    // Your solution here, but you should probably leave the following line
    //   at the end.
	//sum up the numbers and call the RunLengthEncoding with 5 arguments
	  int size = sea.width() * sea.height();
	  int[] type = new int[size];
	  int counter = 0;
	  int new_size = 1;
	  
	  //convert the 2D array into a 1D array
	  for (int i = 0; i < sea.height(); i++){
		  for (int j = 0; j < sea.width(); j++){
			  int content = sea.cellContents(j,i);
			  if (content == Ocean.EMPTY){
				  type[counter] = 0; 
			  }
			  else if (content == Ocean.FISH){
				  type[counter] = 2;    
			  }
			  
			  else if (content == Ocean.SHARK){
				  type[counter] = 3+sea.sharkFeeding(j, i);   //store the value of shark hunger
			  }
			  counter ++;
		  }
	  }
	  
	  //Calculate the length of array needed to store the run length encoding of the ocean
	  for (int i=1; i<type.length; i++){
		  if (type[i] != type[i-1])
			  new_size ++;
	  }
	  
	  //Create 2 arrays of length "new_size" to store the respective species and it's size
	  int[] length = new int[new_size];
	  int[] species = new int[new_size];
	  counter = 0;
	  
	  //compare the elements in the type array and sum up into a shorter array
	  if(type.length != 0){
		  species[0] = type[0];
		  length[0] = 1;
	  }
	  
	  //if subsequent species are the same, increase the size, else store the next species in the next cell and 
	  //reset the value in the respective cell of the length array to 1
	  for (int i=1; i<type.length; i++){
		  if(type[i]==species[counter]){
			  length[counter] += 1;
		  }
		  else{
			  counter++;
			  species[counter]=type[i];
			  length[counter]=1;
		  }
	  }

	  buildDList(sea.width(),sea.height(),sea.starveTime(),species, length);
	  
    check();
  }

  /**
   *  The following methods are required for Part IV.
   */

  /**
   *  addFish() places a fish in cell (x, y) if the cell is empty.  If the
   *  cell is already occupied, leave the cell as it is.  The final run-length
   *  encoding should be compressed as much as possible; there should not be
   *  two consecutive runs of sharks with the same degree of hunger.
   *  @param x is the x-coordinate of the cell to place a fish in.
   *  @param y is the y-coordinate of the cell to place a fish in.
   */

  public void addFish(int x, int y) {
    // Your solution here, but you should probably leave the following line
    //   at the end.
	  curr = list.head;
	  int grid_number = y * height + x + 1;		//change the coordinates into cell number
	  if (grid_number == 1){
		  insertFront(grid_number, Ocean.FISH);
	  }
	  else{
	  while (curr.next!=null){
			  curr = curr.next;
			  grid_number = grid_number - curr.getSize();	//move along the linked list and check if the particular cell is empty	
			  if(grid_number > 0 && curr.getSpecies() != Ocean.EMPTY){
				  continue;
			  }
			  //cell is occupied, return without doing anything
			  if (grid_number <= 0 && curr.getSpecies() != Ocean.EMPTY){
				  continue;
			  }
			  //if there is a single empty cell and the fish/shark needs to get inserted into that cell
			  if(curr.getSpecies() == Ocean.EMPTY && curr.getSize() == 1){
				  replacesEmptyCell(grid_number, Ocean.FISH);
				  break;
			  }
			  //insert  fish/shark at the front of the list of empty cells
			  else if (grid_number < 0 && curr.getSize() > 1 && grid_number == (curr.getSize()-1)*-1 && curr.getSpecies() == Ocean.EMPTY){
				  frontOfEmptyNode(grid_number, Ocean.FISH);
				  break;
			  }
			  //insert fish/shark at the end of the list of empty cells
			  else if (grid_number == 0 && curr.getSize() > 1 && curr.getSpecies() == Ocean.EMPTY){
				  endOfEmptyNode(grid_number, Ocean.FISH);
				  break;
			  }
			  //insert fish/shark in the middle of the list of empty cells
			  else if (grid_number < 0){
				  insertMiddle(grid_number, Ocean.FISH);
				  break;
			  }
	  }
	  }
	  check();
  }

  
  //insert fish/shark at (0,0)
  public void insertFront(int grid_number, int species){
	  if (curr.next.getSpecies() != Ocean.EMPTY){
		  return;
	  }
	  else if (curr.next.getSpecies() == species && curr.next.getSize()==1){
		  DListNode newNode = new DListNode(species, 1, curr.next.next, list.head);
		  curr.next = newNode;
	  }
	  else if (curr.next.getSpecies() == Ocean.EMPTY && curr.next.getSize() > 1){
		  curr.next.setSize(curr.next.getSize()-1);
		  DListNode newNode = new DListNode(species, 1, curr.next, list.head);
		  curr.next.prev = newNode;
		  curr.next = newNode;
	  }
  }
  
  //size of the empty node is 1, and the fish/shark node replaces that empty node
  public void replacesEmptyCell(int grid_number, int species){
	  if (curr.prev.getSpecies()==species && curr.next.getSpecies()==species){
		  curr.prev.setSize(curr.prev.getSize() + curr.next.getSize() + 1);
		  curr.prev.next = curr.next.next;
		  if (curr.next.next != null){
		  curr.next.next.prev = curr.prev;
		  curr = curr.prev;
		  }
	  }
	  else if (curr.prev.getSpecies()==species && curr.next.getSpecies()!=species){
		  curr.prev.setSize(curr.prev.getSize()+1);
		  curr.prev.next = curr.next;
		  curr.next.prev = curr.prev;
	  }
	  else if (curr.next.getSpecies()==species && curr.prev.getSpecies()!=species){
		  curr.next.setSize(curr.next.getSize()+1);
		  curr.prev.next = curr.next;
		  curr.next.prev = curr.prev;
	  }
  }
  
  //insert shark/fish at the front of the list of empty cells
  public void frontOfEmptyNode (int grid_number, int species){
	  if (curr.prev.getSpecies() == species && curr.prev.getHunger() == 0){
		  curr.prev.setSize(curr.prev.getSize()+1);
		  curr.setSize(curr.getSize()-1);
	  }
	  else if (curr.prev.getSpecies() != species){
		  curr.setSize(curr.getSize()-1);
		  DListNode newNode = new DListNode(species,1,curr.prev,curr);
		  curr.prev.next = newNode;
		  curr.prev = newNode;
	  }
  }
  
  //insert shark/fish at the front of the list of empty cells
  public void endOfEmptyNode (int grid_number, int species){
	  if (curr.next.getSpecies() == species && curr.next.getHunger() == 0){
		  curr.next.setSize(curr.next.getSize()+1);
		  curr.setSize(curr.getSize()-1);
	  }
	  else {
		  curr.setSize(curr.getSize()-1);
		  DListNode newNode = new DListNode(species,1,curr.next,curr);
		  curr.next.prev = newNode;
		  curr.next = newNode;
	  }
  }
  
  //insert shark/fish in the middle of the list of empty cells
  public void insertMiddle (int grid_number, int species){
	  DListNode newNode1 = new DListNode(species, 1, null, curr);
	  DListNode newNode2 = new DListNode(Ocean.EMPTY, Math.abs(grid_number), curr.next, newNode1);
	  curr.setSize(curr.getSize()-1-newNode2.getSize());
	  newNode1.next = newNode2;
	  curr.next.prev = newNode2;
	  curr.next = newNode1;
  }

  /**
   *  addShark() (with two parameters) places a newborn shark in cell (x, y) if
   *  the cell is empty.  A "newborn" shark is equivalent to a shark that has
   *  just eaten.  If the cell is already occupied, leave the cell as it is.
   *  The final run-length encoding should be compressed as much as possible;
   *  there should not be two consecutive runs of sharks with the same degree
   *  of hunger.
   *  @param x is the x-coordinate of the cell to place a shark in.
   *  @param y is the y-coordinate of the cell to place a shark in.
   */

  public void addShark(int x, int y) {
    // Your solution here, but you should probably leave the following line
    //   at the end.
	  curr = list.head;
	  int grid_number = y * height + x + 1;		//change the coordinates into cell number (height * width + width)
	  if (grid_number == 1){
		  insertFront(grid_number, Ocean.SHARK);
	  }
	  else{
	  while (curr.next!=null){
			  curr = curr.next;
			  grid_number = grid_number - curr.getSize();	//move along the linked list and check if the particular cell is empty	
			  if(grid_number > 0 && curr.getSpecies() != Ocean.EMPTY){
				  continue;
			  }
			  //cell is occupied, return without doing anything
			  if (grid_number <= 0 && curr.getSpecies() != Ocean.EMPTY){
				  continue;
			  }
			  //if there is a single empty cell and the fish/shark needs to get inserted into that cell
			  if(curr.getSpecies() == Ocean.EMPTY && curr.getSize() == 1){
				  replacesEmptyCell(grid_number, Ocean.SHARK);
				  break;
			  }
			  //insert  fish/shark at the front of the list of empty cells
			  else if (grid_number < 0 && curr.getSize() > 1 && grid_number == (curr.getSize()-1)*-1 && curr.getSpecies() == Ocean.EMPTY){
				  frontOfEmptyNode(grid_number, Ocean.SHARK);
				  break;
			  }
			  //insert fish/shark at the end of the list of empty cells
			  else if (grid_number == 0 && curr.getSize() > 1 && curr.getSpecies() == Ocean.EMPTY){
				  endOfEmptyNode(grid_number, Ocean.SHARK);
				  break;
			  }
			  //insert fish/shark in the middle of the list of empty cells
			  else {
				  insertMiddle(grid_number, Ocean.SHARK);
				  break;
			  }
	  }
	  }
    check();
  }

  /**
   *  check() walks through the run-length encoding and prints an error message
   *  if two consecutive runs have the same contents, or if the sum of all run
   *  lengths does not equal the number of cells in the ocean.
   */

  public void check() {
	  curr = list.head;
	  int sum = 0;
	  //check if the contents of 2 consecutive nodes are the same
	  while(curr.next != null){
		  curr = curr.next;
		  if (curr.next != null){
		  if (curr.getSpecies() == curr.next.getSpecies() && curr.getHunger() == curr.next.getHunger()){
			  System.out.println("ERROR!! 2 consecutive runs have the same contents!!");
			  break;
		  }
		  sum += curr.getSize();
	  }
	  }
	  sum += curr.getSize();
	  //check if the sum of sizes are equal to the size of the ocean
	  if (sum != width*height){
		  System.out.println("ERROR!! Sum of lengths not equal to number of cells in ocean!!");
		  System.out.println("Sum: " + sum + "Total: " + width*height);
	  }  
	  
	  restartRuns();
  }

}
