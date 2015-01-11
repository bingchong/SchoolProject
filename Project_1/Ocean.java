/* Ocean.java */
//Name: Bing Chong Lim				Student ID: 22789609
//Project 1							Class Account: cs61b-gw
/**
 *  The Ocean class defines an object that models an ocean full of sharks and
 *  fish.  Descriptions of the methods you must implement appear below.  They
 *  include a constructor of the form
 *
 *      public Ocean(int i, int j, int starveTime);
 *
 *  that creates an empty ocean having width i and height j, in which sharks
 *  starve after starveTime timesteps.
 *
 *  See the README file accompanying this project for additional details.
 */

public class Ocean {

  /**
   *  Do not rename these constants.  WARNING:  if you change the numbers, you
   *  will need to recompile Test4.java.  Failure to do so will give you a very
   *  hard-to-find bug.
   */

  public final static int EMPTY = 0;
  public final static int SHARK = 1;
  public final static int FISH = 2;

  /**
   *  Define any variables associated with an Ocean object here.  These
   *  variables MUST be private.
   */
  private int width;
  private int height;
  private int hunger;
  private Object [][] ocean;


  /**
   *  The following methods are required for Part I.
   */

  /**
   *  Ocean() is a constructor that creates an empty ocean having width i and
   *  height j, in which sharks starve after starveTime timesteps.
   *  @param i is the width of the ocean.
   *  @param j is the height of the ocean.
   *  @param starveTime is the number of timesteps sharks survive without food.
   */

  public Ocean(int i, int j, int starveTime) {
    // Your solution here.
	  width = i;
	  height = j;
	  hunger = starveTime;
	  ocean = new Object[i][j];
	  
  }

  /**
   *  width() returns the width of an Ocean object.
   *  @return the width of the ocean.
   */

  public int width() {
    // Replace the following line with your solution.
    return width;
  }

  /**
   *  height() returns the height of an Ocean object.
   *  @return the height of the ocean.
   */

  public int height() {
    // Replace the following line with your solution.
    return height;
  }

  /**
   *  starveTime() returns the number of timesteps sharks survive without food.
   *  @return the number of timesteps sharks survive without food.
   */

  public int starveTime() {
    // Replace the following line with your solution.
    return hunger;
  }

  /**
   *  addFish() places a fish in cell (x, y) if the cell is empty.  If the
   *  cell is already occupied, leave the cell as it is.
   *  @param x is the x-coordinate of the cell to place a fish in.
   *  @param y is the y-coordinate of the cell to place a fish in.
   */

  public void addFish(int x, int y) {
    // Your solution here.
	  if (cellContents(x,y)==EMPTY){
		   ocean[x][y] = Ocean.FISH;
	  }
	  return;
  }
  

  /**
   *  addShark() (with two parameters) places a newborn shark in cell (x, y) if
   *  the cell is empty.  A "newborn" shark is equivalent to a shark that has
   *  just eaten.  If the cell is already occupied, leave the cell as it is.
   *  @param x is the x-coordinate of the cell to place a shark in.
   *  @param y is the y-coordinate of the cell to place a shark in.
   */
  
  //Inner class to contain the hunger value of Shark
  class Shark{
	  private int hunger;
	  
	  public Shark(int hunger_time){
		  hunger = hunger_time;
	  }
	  public int getHungerValue(){
		  return hunger;
	  }
	  public void setHungerValue(int value){
		  hunger = value;
	  }
  }
  
  //If cell is empty, add in a Shark
  public void addShark(int x, int y) {
    // Your solution here.
	  if (cellContents(x,y)==EMPTY)
		  ocean[x][y] = new Shark(0);
	  return;
  }

  /**
   *  cellContents() returns EMPTY if cell (x, y) is empty, FISH if it contains
   *  a fish, and SHARK if it contains a shark.
   *  @param x is the x-coordinate of the cell whose contents are queried.
   *  @param y is the y-coordinate of the cell whose contents are queried.
   */

  public int cellContents(int x, int y) throws NullPointerException{
    // Replace the following line with your solution.
	  try{
		  x = warpWidth(x);				//warp the width
		  y = warpHeight(y);			//warp the height
		  Object s = new Shark(0);
		  if (ocean[x][y].getClass().equals(s.getClass()))		//if cell contains an object of class Shark, return Shark
			  return SHARK;
		  else if (Integer.parseInt(ocean[x][y].toString()) == FISH)
			  return FISH;
		  else
			  return EMPTY;
	  }
	  catch (NullPointerException ex){
		  return EMPTY;
	  }
  }

  //Warp the Width to be within the range of the 2D array
  public int warpWidth (int x){
	  if (x < 0)
		  return (x % width)+width;
	  else
		  return x % width;
  }
  
  //Warp the Height to be within the range of the 2D array
  public int warpHeight (int y){
	  if (y < 0)
		  return (y % height) + height;
	  else
		  return y % height;
  }
  
  /**
   *  timeStep() performs a simulation timestep as described in README.
   *  @return an ocean representing the elapse of one timestep.
   */

  public Ocean timeStep() {
    // Replace the following line with your solution.
	  
	  Ocean ocean_end_phase = new Ocean(width, height, hunger);
	  for (int i = 0; i < width; i++)
		  for (int j = 0; j < height; j++)
			  ocean_end_phase.ocean[i][j] = ocean[i][j];      //duplicate ocean to implement changes
	  Events(ocean_end_phase.ocean);						  //impose the rules and do some modifications
    return ocean_end_phase;
  }

  //Calculate the number of fishes that are neighbors
  public int fishNeighbors(int i, int j){
	  int counter = 0;
	  if (cellContents(i+1, j-1)== FISH)
		  counter += 1;
	  if (cellContents(i, j-1)==FISH)
		  counter += 1;
	  if (cellContents(i-1, j-1)==FISH)
		  counter += 1;
	  if (cellContents(i-1, j)==FISH)
		  counter += 1;
	  if (cellContents(i-1, j+1)==FISH)
		  counter += 1;
	  if (cellContents(i, j+1)==FISH)
		  counter += 1;
	  if (cellContents(i+1, j+1)==FISH)
		  counter += 1;
	  if (cellContents(i+1, j)==FISH)
		  counter += 1;
	  return counter;
  }
  
  ////Calculate the number of sharks that are neighbors
  public int sharkNeighbors(int i, int j){
	  int counter = 0;
	  if (cellContents(i+1, j-1)== SHARK)
		  counter += 1;
	  if (cellContents(i, j-1)==SHARK)
		  counter += 1;
	  if (cellContents(i-1, j-1)==SHARK)
		  counter += 1;
	  if (cellContents(i-1, j)==SHARK)
		  counter += 1;
	  if (cellContents(i-1, j+1)==SHARK)
		  counter += 1;
	  if (cellContents(i, j+1)==SHARK)
		  counter += 1;
	  if (cellContents(i+1, j+1)==SHARK)
		  counter += 1;
	  if (cellContents(i+1, j)==SHARK)
		  counter += 1;
	  return counter;
  }
  
  public void Events(Object[][] x){
	  for (int i = 0; i < width; i++){
		  for (int j = 0; j < height; j++){
			  if (cellContents(i,j)==SHARK){
				  Shark s = (Shark) x[i][j];       //Type-casting to implement methods inside Shark object
				  if (fishNeighbors(i,j) >= 1){
					  s.setHungerValue(0);
				  }
				  else{
					  s.setHungerValue(s.getHungerValue()+1);  //Increment the hunger value by 1
					  if (s.getHungerValue() > this.starveTime())                     //If HungerValue > hunger, Shark dies
						  x[i][j] = EMPTY;
				  }
			  }
			  
			  if (cellContents(i,j)==FISH){
				  if (sharkNeighbors(i,j) >=2){
					  x[i][j] = new Shark(0);
				  }
				  else if (sharkNeighbors(i,j) == 1){
					  x[i][j] = EMPTY;
				  }
			  }
			  
			  if (cellContents(i,j)==EMPTY){
				  if (sharkNeighbors(i,j) >= 2 && fishNeighbors(i,j) >= 2){
					  x[i][j] = new Shark(0);
				  }
				  else if (fishNeighbors(i,j) >= 2 && sharkNeighbors(i,j) <= 1){
					  x[i][j] = FISH;
				  }
			  }
		  }
	  }
  }
  
  /**
   *  The following method is required for Part II.
   */

  /**
   *  addShark() (with three parameters) places a shark in cell (x, y) if the
   *  cell is empty.  The shark's hunger is represented by the third parameter.
   *  If the cell is already occupied, leave the cell as it is.  You will need
   *  this method to help convert run-length encodings to Oceans.
   *  @param x is the x-coordinate of the cell to place a shark in.
   *  @param y is the y-coordinate of the cell to place a shark in.
   *  @param feeding is an integer that indicates the shark's hunger.  You may
   *         encode it any way you want; for instance, "feeding" may be the
   *         last timestep the shark was fed, or the amount of time that has
   *         passed since the shark was last fed, or the amount of time left
   *         before the shark will starve.  It's up to you, but be consistent.
   */

  public void addShark(int x, int y, int feeding) {
    // Your solution here.
	  if (cellContents(x,y)==EMPTY)
		  ocean[x][y] = new Shark(feeding);
	  return;
  }

  /**
   *  The following method is required for Part III.
   */

  /**
   *  sharkFeeding() returns an integer that indicates the hunger of the shark
   *  in cell (x, y), using the same "feeding" representation as the parameter
   *  to addShark() described above.  If cell (x, y) does not contain a shark,
   *  then its return value is undefined--that is, anything you want.
   *  Normally, this method should not be called if cell (x, y) does not
   *  contain a shark.  You will need this method to help convert Oceans to
   *  run-length encodings.
   *  @param x is the x-coordinate of the cell whose contents are queried.
   *  @param y is the y-coordinate of the cell whose contents are queried.
   */

  public int sharkFeeding(int x, int y) {
    // Replace the following line with your solution.
	  if (cellContents(x,y) == SHARK){
		  Shark s = (Shark) ocean[x][y];
		  return s.getHungerValue();
	  }
	  else
		  return 999;
  }
  
}

