/* GameBoard.java */
//Names: Bing Chong Lim   (cs61b-gw) 
//		 Joy Jeng		  (cs61b-gu)
//		 Jin Myung Kwak	  (cs61b-xv)
package player;
import list.*;


/**
 *  Part of an implementation of an automatic Network player.  Keeps track of moves
 *  made by both players in an internal game board, and can determine whether the 
 *  board has a winning network.  Also uses the current board to evaluate the chance 
 *  of winning.
 */
public class GameBoard {

	protected final static int BLACK = 0;
	protected final static int WHITE = 1;
	protected final static int EMPTY = 10;
	protected final static int DIMENSION = 8;
	protected int[][] board = new int[DIMENSION][DIMENSION]; // the board is 8x8, with 0-7

	protected int[] currentNumberPieces = {0, 0};
	protected boolean isEmpty;

	//Default constructor for GameBoard that initializes an empty board
	public GameBoard() {
		for (int i = 0; i < DIMENSION; i++) {
			for (int j = 0; j < DIMENSION; j++ ) {
				board[i][j] = EMPTY;
			}
		}
		isEmpty = true;
	}

	//Constructor for GameBoard that takes in a list of Coordinate objects and initializes the gameboard accordingly
	//Reads the coordinates and color from the Coordinate object and places the respective pieces on the current game board
	public GameBoard(Coordinate[] c) {
		this();
		for (int i = 0; i < c.length; i++) {
			Move move = new Move(c[i].x, c[i].y);
			updateBoard(move, c[i].color);
		}
	}

	//Creates a new GameBoard object that is a replicate of the current GameBoard and return it
	protected GameBoard copy() {
		GameBoard n = new GameBoard();
		for (int x = 0; x < DIMENSION; x++){
			for (int y =0; y < DIMENSION; y++){
				if(this.board[x][y] != EMPTY){
					n.updateBoard(new Move(x,y), this.board[x][y]);
				}
			}
		}
		return n;
	}

	//Sums up the number of black and white pieces on the board
	//Returns the total number of pieces on the board
	protected int totalNumPieces(){
		return currentNumberPieces[0]+currentNumberPieces[1];
	}

	/* Determines whether a move is valid.
	 Takes in a move object and checks if the move is a valid move (fulfills the rules of the game)
     If the move is a step move, it uses the isValidStepMove method to validate the step move
     Returns false if the move does not fulfills the conditions
     Returns true if the move fulfills the conditions
	 */
	protected boolean isValidMove(Move move, int color) {

		// not within dimensions of the gameboard
		if (move.x1 < 0 || move.x1 >= DIMENSION || move.y1 < 0 || move.y1 >= DIMENSION) {
			return false;
		}

		// No chip may be placed in any of the four corners.
		if ((move.x1 == 0 && move.y1 == 0) || (move.x1 == 0 && move.y1 == (DIMENSION - 1)) || (move.x1 == (DIMENSION - 1) && move.y1 == 0) || (move.x1 == (DIMENSION - 1) && move.y1 == (DIMENSION - 1))) {
			return false;
		}

		// No chip may be placed in a goal of the opposite color.
		if (color == BLACK && (move.x1 == 0 || move.x1 == (DIMENSION - 1))) {
			return false;
		}
		if (color == WHITE && (move.y1 == 0 || move.y1 == (DIMENSION - 1))) {
			return false;
		}

		// No chip may be placed in a square that is already occupied.
		if (board[move.x1][move.y1] != EMPTY) {
			return false;
		}

		// check if there are any clusters of three pieces
		if(!(notThreeInCluster(move.x1, move.y1, color))){
			return false;
		}
		// Check if it is a valid stepmove if all pieces are placed
		// If it is not a valid stepmove, return false	
		if (move.moveKind == 2){

			return isValidStepMove(move, color);
		}


		return true;
	}

	//Takes in a step move and checks if it is a valid move
	//Returns true if the move is valid
	//Returns false if the move is not valid
	protected boolean isValidStepMove(Move move, int color){	

		GameBoard gBoard = this.copy();
		gBoard.board[move.x2][move.y2] = EMPTY;
		Move m = new Move(move.x1, move.y1);
		if(gBoard.isValidMove(m, color)){
			return true;
		}
		else{
			return false;
		}
	}

	//Takes in a 'x' and 'y' coordinate and checks if there are 3 pieces of the same color being side by side
	//Calls the checkNeighbors method to obtain the number of pieces that are of the same color surrounding the current piece
	protected boolean notThreeInCluster(int x, int y, int color){
		// cannot form 3 in a cluster
		List neighbors = checkNeighbors(x, y, color);
		if (neighbors.length() >= 2) {
			return false;
		} else if (neighbors.length() == 1) {
			ListNode neighbor = neighbors.front();
			try {
				int neighborX = ((Coordinate) neighbor.item()).x;
				int neighborY = ((Coordinate) neighbor.item()).y;
				List otherNeighbors = checkNeighbors(neighborX, neighborY, color);
				if (otherNeighbors.length() >= 1) {
					return false;
				}
			} catch (InvalidNodeException e) {
				//
			}  
		}
		return true;
	}

	/**Creates a list that stores the coordinates of the surrounding pieces that has the same color as the current piece
	 *Checks the grids surrounding the current piece
	 *If the adjacent piece has the same color, it stores the coordinates and color into a Coordinate object and appends it into a list
	 *Returns the list of Coordinates objects that contains the information of the pieces adjacent to the current piece
	 *Check the number of pieces around the current piece and returns a list that contains the coordinates.
	 */
	private List checkNeighbors(int x, int y, int color) {
		List list = new DList();
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {

				// don't check self, only check for specified color, don't go off the board
				if (!(i == x && j == y) && i >= 0 && i <= (DIMENSION - 1) && j >= 0 && j <= (DIMENSION - 1) && board[i][j] == color) {
					Coordinate item = new Coordinate(i, j, color);
					list.insertBack(item);
				}
			}
		}
		return list;
	}

	/**Checks the board for all the valid moves for a particular color
	 *Iterates through all the board and uses isValidMove method to check if the move is a valid move
	 *If it is a valid move, add the move object into the list
	 *Returns a list containing all the possible moves for the specified color
	 *Generates a list of valid moves for the specificed color
	 */
	protected List validMoves(int color){
		List vMoves = new DList();
		//Creates a DList of valid add moves
		if(currentNumberPieces[0] + currentNumberPieces[1] < 20){
			for(int i = 0; i < DIMENSION; i++){
				for(int j = 0; j < DIMENSION; j++){
					Move move = new Move(i,j);
					if(isValidMove(move, color)){
						((DList)vMoves).insertBack(move);
					}
				}	
			}  
			return vMoves;
		}

		//Creates a DList of valid step moves
		else {
			List validMoves = new DList();
			for(int i = 0; i < DIMENSION; i++){
				for(int j = 0; j < DIMENSION; j++){
					if(board[i][j] == color){
						for (int x = 0; x < DIMENSION; x++) {
							for (int y = 0; y < DIMENSION; y++) {
								if (!(x == i && y == j)) {
									Move stepmove = new Move(x,y,i,j);
									if(isValidMove(stepmove, color)){
										((DList)validMoves).insertBack(stepmove);
									}
								}
							}
						}
					}
				}
			}
			return validMoves;  
		}
	}


	// Takes an input of a move and a color, and updates the internal board accordingly.
	protected void updateBoard(Move move, int color) {
		int x = move.x1;
		int y = move.y1;
		int xOriginal = move.x2;
		int yOriginal = move.y2;

		if (move.moveKind == Move.QUIT) {
			return;
		}

		// if not a valid move, don't change the board
		if (!(isValidMove(move, color))) {
			return;
		}
		isEmpty = false;

		// add move
		if (move.moveKind == Move.ADD) {
			currentNumberPieces[color]++;
			board[x][y] = color;
		}  

		// step move
		if (move.moveKind == Move.STEP) {
			// check that there is a piece in the original location in the correct color
			// remove that piece
			// call "addPiece" to add the piece to the grid
			board[xOriginal][yOriginal] = EMPTY;
			board[x][y] = color;
		}

	}

	/**Generates a score for the current board
	 *Uses helper functions: checkHasNetwork(), checkConnections(), checkGoals(), numOfValidMoves(), and checkConnectionOfGoals() 
	 *to generate the score for the current board
	 */
	protected double evaluation(){
		double score = 0;

		score += checkHasNetwork();

		//If any of the player has a network, return score since game has ended
		if (score != 0){
			return score; 
		}

		//check the connections, goals, total number of goals, and total number of valid moves 
		//that each color has and add it to total score

		score += checkConnections(); 
		score += checkGoals();
		score += numOfValidMoves();
		score += checkConnectionsOfGoals();


		return score;
	}

	//Check if there is any existing network on the board
	//If there is a network, the player wins right away. Therefore, gets assigned the highest/lowest possible score, depending on the player
	private double checkHasNetwork(){
		if(hasANetwork(BLACK)){   //Black wins, assign min score (Opponent)
			return -1;
		}
		else if (hasANetwork(WHITE)){ //White wins, assign max score (MachinePlayer)
			return 1;
		}
		else {
			return 0;
		}
	}

	/**Generate a score for the current board depending solely on the total number of connections that each side has
	 *The greater the number of connections, the higher chance of forming a network and therefore has a greater chance of winning
	 *Return a score for the current board, depending only on the total number of connections each side has
	 */
	private double checkConnections(){
		double total = 0;
		double WHITE_points = 0.01;
		double BLACK_points = -0.01;
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[0].length; j++){

				//if board contains white piece, check for connections and add a specific score to total
				if(board[j][i] != EMPTY && board[j][i] == WHITE){
					int count = 0;
					Coordinate [] num_connections;
					Coordinate coor  = new Coordinate(j,i,WHITE);
					num_connections = connections(coor);
					for(int c = 0; c < num_connections.length; c++){
						if(num_connections[c] != null){
							count++;
						}
					}
					total += count * WHITE_points;
				}

				//if board contains black piece, check for connections and add a specific score to total
				else if (board[j][i] != EMPTY && board[j][i] == BLACK){
					int count = 0;
					Coordinate [] num_connections;
					Coordinate coor  = new Coordinate(j,i,BLACK);
					num_connections = connections(coor);
					for(int c = 0; c < num_connections.length; c++){
						if(num_connections[c] != null){
							count++;
						}
					}
					total += count * BLACK_points;
				}
			}
		}
		return total;
	}

	/**Assign a score to the current board depending solely on the number of connections the goal pieces have
	 *If the goal pieces have more connections, there are more ways to form a network, leading to a higher chance of winning 
	 *Return a score for the current board, depending only on the number of connections the goal pieces have
	 */
	private double checkConnectionsOfGoals(){
		double total = 0;
		double WHITE_points = 0.03;
		double BLACK_points = -0.03;
		//Check Black's goal area for pieces and find the number of connections from the goal area
		for(int i = 0; i < board[0].length; i+=7){
			for(int j = 0; j < board.length; j++){
				//if board contains black piece, check for connections and add a specific score to total
				if(board[j][i] != EMPTY && board[j][i] == BLACK){
					int count = 0;
					Coordinate [] num_connections;
					Coordinate coor  = new Coordinate(j,i,BLACK);
					num_connections = connections(coor);
					for(int c = 0; c < num_connections.length; c++){
						if(num_connections[c] != null){
							count++;
						}
					}
					total += count * BLACK_points;
				}
				//if board contains white piece, check for connections and add a specific score to total
				if(board[i][j] != EMPTY && board[i][j] == WHITE){
					int count = 0;
					Coordinate [] num_connections;
					Coordinate coor  = new Coordinate(i,j,WHITE);
					num_connections = connections(coor);
					for(int c = 0; c < num_connections.length; c++){
						if(num_connections[c] != null){
							count++;
						}
					}
					total += count * WHITE_points;
				}
			}
		}
		return total;
	}

	/**Check the number of pieces in the goal area and assign a score to the current board
	 *Having 1 piece at each side of the goal area increases the chances of winning
	 *If there are too many pieces in the goal area, it would reduce the chances of winning, 
	 *since there are lesser pieces in the middle of the board to form a network with the goal pieces
	 *Returns the score of the current board, depending only on the number of pieces in the goal area
	 */
	private double checkGoals(){
		double total = 0;
		double WHITE_points = 0.02;
		double BLACK_points = -0.02;
		int topPieces = 0;
		int bottomPieces = 0;
		int leftmostPieces = 0;
		int rightmostPieces = 0;

		//Check the top and bottom rows 
		for(int j = 1; j < board[0].length; j++){
			if(board[j][0] == BLACK){
				topPieces += 1;
			}
			if(board[j][board.length-1] == BLACK){
				bottomPieces += 1;
			}
		}

		//Check the leftmost and rightmost columns 
		for(int j = 1; j < board.length; j++){
			if(board[0][j] == WHITE){
				leftmostPieces += 1;
			}
			if(board[board.length-1][j] == WHITE){
				rightmostPieces += 1;
			}
		}

		//Having 1 piece at each side of goal area has a higher chance of winning
		if(topPieces >= 1 && bottomPieces >= 1){
			total += BLACK_points * topPieces + BLACK_points * bottomPieces - .3;
		}
		//Having too many pieces in the goal area will decrease the chance of winning
		//(this is bad because there aren't as many pieces in the middle)
		if(topPieces + bottomPieces >= 3){
			total -= BLACK_points * topPieces + BLACK_points * bottomPieces - .1;
		}

		//Having 1 piece at each side of goal area has a higher chance of winning
		if(leftmostPieces >= 1 && rightmostPieces >= 1){
			total += WHITE_points * leftmostPieces + WHITE_points * rightmostPieces +.3;
		}
		//Having too many pieces in the goal area will decrease the chance of winning
		if(leftmostPieces + rightmostPieces >= 3){
			total -= WHITE_points * leftmostPieces + WHITE_points * rightmostPieces + .1;
		}

		return total;
	}

	/**Check the total number of valid moves available for each player and assign a score to the current board
	 *With greater number of valid moves, there are more choices to place the pieces and come up with a better way to win
	 *Returns the score of the current board, depending only on the total number of valid moves for both players
	 */
	private double numOfValidMoves(){
		double total = 0;
		double WHITE_points = 0.001;
		double BLACK_points = -0.001;

		//Iterate through the 2D array and check for valid moves for both White and Black pieces
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[0].length; j++){
				Move m = new Move(j,i);
				if(isValidMove(m, WHITE)){
					total += WHITE_points;
				}
				if(isValidMove(m, BLACK)){
					total += BLACK_points;
				}
			}
		}
		return total;
	}



	/**
	 *  Generates an array of 8 pieces that the given piece forms a connection with.
	 *  The picture shows which direction each array index corresponds with.
	 *   \   |   /
	 *    7  0  1
	 *     -----
	 *  -6 | X | 2 -
	 *     -----
	 *    5  4  3
	 *   /   |   \   
	 */

	/**Check if the current piece is connected to any other pieces of the same color
	 *Used an array of length 8 to represent the 8 directions that the current piece can connect
	 *Stores the reference to the coordinate object that forms a connection with the current piece in the direction corresponding to the index of the array
	 *Returns the list of Coordinate objects that forms connection with the current piece
	 */
	protected Coordinate[] connections(Coordinate coor) {  
		Coordinate[] c = new Coordinate[8];

		for (int i = 0; i < 8; i++) {
			c[i] = findSameColor(coor.x, coor.y, i, coor.color);
		}  
		return c;
	}

	/**Checks the 8 different directions for pieces of the same color as the current piece
	 *If an opponent's piece is blocking the path, it will proceed on to the next direction and re-evaluates
	 *If it finds a piece of the same color, it means that there is a connection between the 2 pieces in the specified direction
	 *Returns a Coordinate object that contains the position and color of the piece that is connected to the current piece
	 */
	private Coordinate findSameColor(int x, int y, int direction, int color) {

		int oppColor = EMPTY;
		if (color == BLACK) {
			oppColor = WHITE;
		} else {
			oppColor = BLACK;
		}

		while (true) {
			switch (direction) {
			case 0:
				y--;
				break;
			case 1:
				x++;
				y--;
				break;
			case 2:
				x++;
				break;
			case 3:
				x++;
				y++;
				break;
			case 4:
				y++;
				break;
			case 5:
				x--;
				y++;
				break;
			case 6:
				x--;
				break;
			case 7:
				x--;
				y--;

			}

			if (!(x >= 0 && x < (DIMENSION) && y >= 0 && y < (DIMENSION))) {
				break;
			}

			if (board[x][y] == oppColor) {
				return null;
			}

			if (board[x][y] == color) {
				return new Coordinate(x, y, color);
			}
		}
		return null;
	}

	/**Check if the current board contains a network for a given color
	 *Starts the evaluation from the first goal area to the next goal area
	 *Uses checkPossibleChain method on the border pieces at the first goal area to check for a network
	 *Returns a boolean that specifies whether there exists a network for the specified color
	 */
	protected boolean hasANetwork(int color) {

		// less than 6 pieces of the color
		if (currentNumberPieces[color] < 6) {
			return false;
		}

		List firstBorder = borderPieces(color, 0);
		List secondBorder = borderPieces(color, DIMENSION - 1);

		// no pieces in at least one of the goal areas
		if (firstBorder.length() == 0 && secondBorder.length() == 0) {
			return false;
		}

		ListNode current = firstBorder.front();
		while (current.isValidNode()) {
			try {
				Coordinate piece = ((Coordinate) current.item());

				// builds a tree out of each border piece, and if one of them
				// contains a valid network, returns true.  
				// Otherwise, checks the tree formed by the next border piece
				if(checkPossibleChains(piece)) {
					return true;
				}      
				current = current.next();
			} catch (InvalidNodeException e) {
				e.printStackTrace();
			}
		}

		// none of the border pieces formed a valid network, so returns false
		return false;
	}

	/**Generates a list of Coordinates objects that stores the position of the boarder pieces
	 *Takes in an argument, color, to specifiy the color of the pieces 
	 *Takes in a second argument, number, to specify the boarder to iterate through 
	 *color is color, number is 0 for the top or left border, DIMENSION-1/HIEGHT-1 for bottom or right border
	 */
	private List borderPieces(int color, int number) {

		assert ((number == 0) || (number == (DIMENSION - 1))): "coordinate" + number + "not border piece";
		List count = new DList();

		if (color == BLACK) {
			int y = number;
			for (int x = 0; x < DIMENSION; x++) {      
				if (board[x][y] == color) {
					Coordinate coor = new Coordinate(x, y, color);
					count.insertBack(coor);
				}
			}
		}

		if (color == WHITE) {  
			int x = number;
			for (int y = 0; y < DIMENSION; y++) {      
				if (board[x][y] == color) {
					Coordinate coor = new Coordinate(x, y, color);
					count.insertBack(coor);
				}
			}      
		}
		return count;
	}

	/**Starts building a chain of connections off a boarder piece
	 *Calls the generateNetworks method to see if the current boarder piece forms a network
	 *Returns a boolean that shows whether a network is formed from the current boarder piece
	 */
	private boolean checkPossibleChains(Coordinate start) {
		int x = start.x;
		int y = start.y;
		int color = start.color;
		List connectionsChain = new DList();
		if (board[x][y] == color) {
			connectionsChain.insertBack(start);

			// try to find a valid network
			if (generateNetworks(connectionsChain, null, start, 100, 101)){
				return true;
			}          
		}
		return false;
	}

	//Helper function for hasANetwork that generates the chain of connections for a piece through recursion and checks if it forms a valid network
	//Returns true if the chain of connections forms a network from 1 goal area to the other
	private boolean generateNetworks(List network, Coordinate parent, Coordinate current, int prevConnectionType, int curConnectionType) {    

		// trying to form a network without a valid piece
		if (current == null) {
			return false;
		}

		// must switch direction each time
		if (curConnectionType == prevConnectionType) {
			return false;
		}

		// if the network has two of the same pieces
		if (hasRepeat(network)) {
			return false;
		}

		// cannot go back to the first border
		if (network.length() != 1 && isFirstBorder(current)) {
			return false;
		}

		// current network has too little pieces to end in a goal area
		if (network.length() < 6 && isSecondBorder(current)) {
			return false;
		}

		// there are only 10 chips for each player
		if (network.length() > 10) {
			System.out.println("Something went wrong, there might be repeat pieces");
			return false;
		}

		// if the network ever gets to a piece that is in the other goal area, and there are 6 or more pieces, YAY
		if (network.length() >= 6 && isSecondBorder(current)) {
			return true;
		}

		// recursively build the rest of the tree of connections
		Coordinate[] children = connections(current);

		return generateNetworks(networkWithEachConnection(children[0], network), current, children[0], curConnectionType, 0) 
		|| generateNetworks(networkWithEachConnection(children[1], network), current, children[1], curConnectionType, 1) 
		|| generateNetworks(networkWithEachConnection(children[2], network), current, children[2], curConnectionType, 2) 
		|| generateNetworks(networkWithEachConnection(children[3], network), current, children[3], curConnectionType, 3) 
		|| generateNetworks(networkWithEachConnection(children[4], network), current, children[4], curConnectionType, 4) 
		|| generateNetworks(networkWithEachConnection(children[5], network), current, children[5], curConnectionType, 5) 
		|| generateNetworks(networkWithEachConnection(children[6], network), current, children[6], curConnectionType, 6) 
		|| generateNetworks(networkWithEachConnection(children[7], network), current, children[7], curConnectionType, 7);
	}

	//Returns the list of connections after appending a new Coordinate object to the old list
	private List networkWithEachConnection(Coordinate current, List oldNetwork) {
		List network = ((DList) oldNetwork).copy();
		network.insertBack(current);
		return network;
	}

	// Checks if the most current listNode is the same as any of the ones before
	// Ensures that there is no repeated pieces in the list of Coordinate objects
	private boolean hasRepeat(List network) {
		try {
			ListNode first = network.front();
			ListNode second = network.back();

			while (first != second && first.isValidNode()) {

				if (((Coordinate)first.item()).equals((Coordinate)second.item())) {
					return true;
				}
				first = first.next();
			}
		} catch (InvalidNodeException e) {
			e.printStackTrace();
		}
		return false;
	}

	//Check if the particular Coordinate object is in the first boarder (top row for BLACK and left-most column for WHITE)
	private boolean isFirstBorder(Coordinate c) {
		if (c.color == BLACK) {
			if (c.y == 0) {
				return true;
			}
		}
		if (c.color == WHITE) {
			if (c.x == 0) {
				return true;
			}
		}
		return false;
	}

	//Check if the particular Coordinate object is in the second boarder (bottom row for BLACK and right-most column for WHITE)
	private boolean isSecondBorder(Coordinate c) {
		if (c.color == BLACK) {
			if (c.y == (DIMENSION - 1)) {
				return true;
			}
		}
		if (c.color == WHITE) {
			if (c.x == (DIMENSION - 1)) {
				return true;
			}
		}
		return false;
	}

	//Prints out the currrent game board
	public void printBoard() {
		System.out.println("   0   1   2   3   4   5   6   7");
		System.out.println(" ---------------------------------");
		for (int i = 0; i < 8; i++) {
			System.out.print(i + "|");
			for (int j = 0; j < 8; j++ ) {
				System.out.print(" " + convertNum(board[j][i]) + " |");
			}
			System.out.println();
			System.out.println(" ---------------------------------");
		}
	}

	//Prints out all the information stored within the list of Coordinates object
	public static void printCoordinates(Coordinate[] c) {
		System.out.print("Connections: ");
		for (int i = 0; i < c.length; i++) {
			if (c[i] != null) {      
				System.out.print(c[i].toString());
			}
		}
		System.out.println();
	}

	//Converts the number representation of the pieces into a String
	public static String convertNum(int num) {
		if (num == GameBoard.BLACK) {
			return "B";
		} else if (num == GameBoard.WHITE) {
			return "W";
		} else {
			return " ";
		}
	}

	//---------------------------------------For TESTING!!!-----------------------------------------------------
	//---------------------------------------For TESTING!!!-----------------------------------------------------
	//---------------------------------------For TESTING!!!-----------------------------------------------------
	//--------------------NOT NECESSARY TO READ THE CODE BEYOND THIS POINT--------------------------------------


	public static void main(String[] args) {
		//GameBoard.runTests();
		//GameBoard.EvalTest();
		//GameBoard.testValidMove();
	}

	//  *******The rest of the file contains the methods used for testing
	protected static void testValidMove(){
		int[] blackArray = {1,0,1,2,1,7,3,0,5,0,4,3,2,4,3,5,3,7,5,7};
		int[] whiteArray = {0,3,1,6,2,1,4,2,5,3,6,6,3,4,2,3,4,6,1,1};
		validMoveTest(blackArray, whiteArray, WHITE);
	}

	protected static void validMoveTest(int[] blackCoor, int[] whiteCoor, int color){
		GameBoard gBoard = new GameBoard(GameBoard.changeArrays(blackCoor, whiteCoor));
		gBoard.printBoard();
		List vMoves = gBoard.validMoves(WHITE);
		System.out.println(gBoard.currentNumberPieces[0] + gBoard.currentNumberPieces[1]);
		System.out.println(vMoves);
	}



	protected static void runTests() {
		GameBoard.printEmptySpace();
		System.out.println("___________Commencing Tests___________");
		GameBoard.printEmptySpace();
		GameBoard.emptyTest();
		GameBoard.connectionTests();
		GameBoard.validMoveTests();
		GameBoard.networkTests();
	}

	protected static void printEmptySpace() {  
		for (int i = 0; i < 3; i++) {
			System.out.println();
		}
	}

	protected static void emptyTest() {
		System.out.println("Empty Test");
		GameBoard test = new GameBoard();
		test.printBoard();
		Coordinate[] emptyArray = {};
		test = new GameBoard(emptyArray);
		test.printBoard();
		GameBoard.printEmptySpace();
	}

	protected static void validMoveTests() {  
		System.out.println("-----Valid Move Tests-------");

		// trying these illegal moves: add to corners, goal areas, many in a row
		int[] blackArray = {0,0,0,1,0,2,0,3,0,4,0,5,0,6,0,7,1,0,1,1,1,2,1,3,1,4,1,5,1,6,1,7};
		int[] whiteArray = {0,0,0,1,0,2,0,3,0,4,0,5,0,6,0,7,1,0,1,1,1,2,1,3,1,4,1,5,1,6,1,7};
		GameBoard b1 = new GameBoard(GameBoard.changeArrays(blackArray, whiteArray));
		b1.printBoard();

		// trying these illegal moves: add to corners, goal areas, many in a row
		int[] black1 = {};
		int[] white1 = {};
		b1 = new GameBoard(GameBoard.changeArrays(black1, white1));
		b1.printBoard();

		GameBoard.printEmptySpace();
	}

	protected static void connectionTests() {  
		System.out.println("-----Connection Tests-------");
		int[] blackArray = {6, 0, 6, 1, 6, 3, 6, 4, 6, 6, 6, 7};
		int[] whiteArray = {};
		GameBoard.connectionTest(blackArray, whiteArray, GameBoard.BLACK);
		int[] bArray = {1, 0, 3, 2, 3, 3, 5, 5, 3, 5, 3, 7};
		int[] wArray = {};
		GameBoard.connectionTest(bArray, wArray, GameBoard.BLACK);
		int[] bArray1 = {1, 0, 3, 2, 3, 3, 5, 5, 3, 5, 3, 7};
		int[] wArray1 = {2, 0, 3, 4, 3, 6};
		GameBoard.connectionTest(bArray1, wArray1, GameBoard.BLACK);
		GameBoard.printEmptySpace();
	}


	protected static void connectionTest(int[] blackCoor, int[] whiteCoor, int color) {
		GameBoard gBoard = new GameBoard(GameBoard.changeArrays(blackCoor, whiteCoor));
		gBoard.printBoard();
		//Print out all the coordinates of the black/white pieces

		for (int i = 0; i < blackCoor.length; i++) {
			int x = blackCoor[i];
			int y = blackCoor[++i];
			Coordinate coor  = new Coordinate(x,y,BLACK);
			Coordinate[] c = gBoard.connections(coor);
			System.out.print("For " + (new Coordinate(x, y, GameBoard.BLACK)).toString());
			GameBoard.printCoordinates(c);
		}
		for (int i = 0; i < whiteCoor.length; i++) {
			int x = whiteCoor[i];
			int y = whiteCoor[++i];
			Coordinate coor  = new Coordinate(x,y,WHITE);
			Coordinate[] c = gBoard.connections(coor);
			System.out.print("For " + (new Coordinate(x, y, GameBoard.WHITE)).toString());
			GameBoard.printCoordinates(c);
		}

	}

	protected static void networkTests() {  
		System.out.println("-----Network Tests-------");

		System.out.println("Test1");
		// empty test (none in goal areas and not enough pieces)
		int[] blackArray = {};
		int[] whiteArray = {};
		GameBoard.networkTest(blackArray, whiteArray, GameBoard.BLACK, false);

		System.out.println("Test2");
		// none in both goal areas
		int[] blackArray1 = {1, 0, 2, 0, 3, 0, 4, 0, 5, 0, 6, 0};
		int[] whiteArray1 = {};
		GameBoard.networkTest(blackArray1, whiteArray1, GameBoard.BLACK, false);

		System.out.println("Test3");
		// pieces in both goal areas but not enough pieces to form a network
		int[] blackArray2 = {1, 0, 2, 0, 1, 7, 2, 7};
		int[] whiteArray2 = {};
		GameBoard.networkTest(blackArray2, whiteArray2, GameBoard.BLACK, false);

		System.out.println("Test4");
		int[] blackArray0 = {6, 0, 6, 1, 6, 3, 6, 4, 6, 6, 6, 7};
		int[] whiteArray0 = {};
		GameBoard.networkTest(blackArray0, whiteArray0, GameBoard.BLACK, false);

		System.out.println("Test5");
		int[] bArray = {1, 0, 3, 2, 3, 3, 5, 5, 3, 5, 3, 7};
		int[] wArray = {};
		GameBoard.networkTest(bArray, wArray, GameBoard.BLACK, true);

		System.out.println("Test6");
		//Contains 2 black pieces in the top goal. Should return false.
		int[] blackArray3 = {1, 0, 1, 2, 3, 0, 3, 4, 5, 4, 5, 7};
		GameBoard.networkTest(blackArray3, whiteArray, GameBoard.BLACK, false);

		System.out.println("Test7");
		//Contains 2 black pieces in the bottom goal. Should return false.
		int[] blackArray4 = {1, 0, 1, 2, 2, 3, 2, 7, 4, 5, 4, 7};
		GameBoard.networkTest(blackArray4, whiteArray, GameBoard.BLACK, false);

		System.out.println("Test8");
		//Network of 7 pieces, contains 2 black pieces in the top goal. Should return false.
		int[] blackArray5 = {1, 0, 1, 2, 3, 0, 3, 4, 5, 6, 5, 7};
		GameBoard.networkTest(blackArray5, whiteArray, GameBoard.BLACK, false);

		System.out.println("Test9");
		//Network of 7 pieces, contains 2 black pieces in the bottom goal. Should return false.
		int[] blackArray6 = {1, 0, 1, 2, 2, 3, 2, 7, 4, 5, 6, 5, 6, 7};
		GameBoard.networkTest(blackArray6, whiteArray, GameBoard.BLACK, false);

		System.out.println("Test10");
		int[] blackArray7 = {1, 0, 2, 1, 0, 3, 4, 3, 2, 1, 2, 7};
		GameBoard.networkTest(blackArray7, whiteArray, GameBoard.BLACK, false);

		System.out.println("Test11");
		//Passes through the top piece (2,1) twice.
		//Network1 : (4,0), (2,2), (2,5), (4,5), (4,0), (5,1), (5,7)
		//Network2 : (4,0), (4,5), (2,5), (2,2), (4,0), (5,1), (5,7)
		int[] blackArray8 = {4, 0, 2, 2, 2, 5, 4, 5, 4, 0, 5, 1, 5, 7};
		GameBoard.networkTest(blackArray8, whiteArray, GameBoard.BLACK, false);

		System.out.println("Test12");
		//Passes through the bottom piece (5,7) twice.
		//Network1 : (4,0), (4,4), (6,6), (5,7), (3,5), (5,7)
		//Network2 : (4,0), (4,4), (3,5), (5,7), (6,6), (4,4), (3,5), (5,7)...(loop)
		int[] blackArray9 = {4, 0, 4, 4, 6, 6, 5, 7, 3, 5, 5, 7};
		GameBoard.networkTest(blackArray9, whiteArray, GameBoard.BLACK, false);

		System.out.println("Test13");
		//Starts and Ends at top piece (1, 0)
		int[] blackArray10 = {1, 0, 1, 2, 3, 4, 3, 6, 5, 4, 1, 0};
		GameBoard.networkTest(blackArray10, whiteArray, GameBoard.BLACK, false);

		System.out.println("Test14");
		//Starts and Ends at leftmost piece (0, 1)
		int[] whiteArray11 = {0, 1, 3, 4, 5, 4, 3, 2, 3, 1, 0, 1};
		GameBoard.networkTest(blackArray, whiteArray11, GameBoard.WHITE, false);

		System.out.println("Test15");
		//Notice that this network can end at (5,7), which will return true 
		//Or, connects back to the top piece (1,0) instead of (5,7), which shouldnt happen
		int[] blackArray12 = {1, 0, 1, 2, 3, 4, 3, 6, 5, 4, 5, 7};
		GameBoard.networkTest(blackArray12, whiteArray, GameBoard.BLACK, true);

		System.out.println("Test16");
		//Starts and Ends opposing goals
		//Notice that this network can end at (7,1), which will return true 
		//Or, connects back to the top piece (0,1) instead of (7,1), which shouldnt happen
		int[] whiteArray13 = {0, 1, 3, 4, 5, 4, 3, 2, 3, 1, 7, 1};
		GameBoard.networkTest(blackArray, whiteArray13, GameBoard.WHITE, true);

		System.out.println("Test17");
		//Black network blocked by white piece
		int[] blackArray14 = {1, 0, 1, 2, 3, 4, 3, 6, 5, 4, 5, 7};
		int[] whiteArray14 = {2, 3};
		GameBoard.networkTest(blackArray14, whiteArray14, GameBoard.BLACK, false);

		System.out.println("Test18");
		//Certain paths blocked by white pieces, but black network still exists
		//Path1 : (1,0), (1,3), (5,3), (3,1), (3,5), (5,7)
		//Path2 : (1,0), (1,3), (3,1), (5,3), (3,5), (5,7)
		//Path3 : (1,0), (1,3), (3,1), (3,5), (5,3), (5,7)
		//Path4 : (1,0), (1,3), (3,5), (3,1), (5,3), (5,7)
		//White pieces blocked Paths 2,3,4. Still exists Path 1, so it should return true. 
		// (Feel free to change the location of the white pieces to try out other cases)
		int[] blackArray15 = {1, 0, 1, 3, 5, 3, 3, 1, 3, 5, 5, 7};
		int[] whiteArray15 = {2, 2, 2, 4};
		GameBoard.networkTest(blackArray15, whiteArray15, GameBoard.BLACK, true);

		System.out.println("Test19");
		int[] blackArray16 = {2, 2, 4, 2};
		int[] whiteArray16 = {0, 2, 1, 3, 5, 3, 3, 1, 6, 1, 7, 2};
		GameBoard.networkTest(blackArray16, whiteArray16, GameBoard.WHITE, false);

		System.out.println("Test20");
		//Certain paths blocked by black pieces, but white network still exists
		//Path1 : (0,2), (1,3), (5,3), (3,1), (3,5), (7,5)
		//Path2 : (0,2), (1,3), (3,1), (5,3), (3,5), (7,5)
		//Path3 : (0,2), (1,3), (3,1), (3,5), (5,3), (7,5)
		//Black pieces blocked Paths 2,3. Still exists Path 1, so it should return true. 
		// (Feel free to change the location of the black pieces to try out other cases)
		int[] blackArray17 = {2, 2};
		int[] whiteArray17 = {0, 2, 1, 3, 5, 3, 3, 1, 3, 5, 7, 5};
		GameBoard.networkTest(blackArray17, whiteArray17, GameBoard.WHITE, true);
	}

	private static Coordinate[] changeArrays(int[] blackCoor, int[] whiteCoor) {
		int numPieces = (blackCoor.length + whiteCoor.length) / 2;
		Coordinate[] coors = new Coordinate[numPieces];
		for (int i = 0; i < blackCoor.length; i++) {
			Coordinate n = new Coordinate(blackCoor[i], blackCoor[++i], GameBoard.BLACK);
			coors[i/2] = n;
		}
		for (int i = 0; i < whiteCoor.length; i++) {
			Coordinate n = new Coordinate(whiteCoor[i], whiteCoor[++i], GameBoard.WHITE);
			coors[((blackCoor.length) / 2) + i/2] = n;
		}
		return coors;
	}

	protected static void networkTest(int[] blackCoor, int[] whiteCoor, int color, boolean expectedResult) {
		Coordinate[] coors = changeArrays(blackCoor, whiteCoor);
		GameBoard gBoard = new GameBoard(coors);
		boolean result = gBoard.hasANetwork(color);

		// Uncomment line below if you only want to display the tests that don't pass
		if (result == expectedResult) { return; }

		System.out.println("Testing for a network for " + convertNum(color));
		gBoard.printBoard();

		if (result != expectedResult) {
			System.out.println("TEST FAILED:");
			System.out.println("Expected result is " + expectedResult + ", got " + result);
		} else {
			System.out.println("TEST PASSED:");
			System.out.println("Expected " + expectedResult + " and got " + result);
		}
		GameBoard.printEmptySpace();
	}

	//set result as 1 if expect a positive number, -1 if expect a negative number, 0 if both have same chances of winning
	protected static void EvalTest(){
		System.out.println("Test 21");
		int[] blackArray = {};
		int[] whiteArray = {};
		int result = 0;			//Empty board, both parties have equal chance of winning. 
		runEvalTests(blackArray, whiteArray, result);

		System.out.println("Test 22");
		int[] b1Array = {1, 0, 2, 7};
		int[] w1Array = {2, 3};
		result = -1;	   //Black has a higher chance of winning (has 2 pieces in goal area)
		runEvalTests(b1Array, w1Array, result);

		System.out.println("Test 23");
		int[] b2Array = {4, 1, 6, 1, 6, 6};
		int[] w2Array = {1, 3, 3, 5, 3, 3};
		result = 1;	   //White has a higher chance of winning (has more connections than black)
		runEvalTests(b2Array, w2Array, result);

		System.out.println("Test 24");
		int[] b3Array = {2, 0, 5, 7, 2, 4};
		int[] w3Array = {0, 2, 7, 5, 4, 2};
		result = 0;	   //Both has same number of connections and same number of pieces in goal area
		runEvalTests(b3Array, w3Array, result);

		System.out.println("Test 25");
		int[] b4Array = {2, 0, 5, 7, 2, 4};
		int[] w4Array = {0, 2, 7, 5, 4, 2, 4, 5};
		result = 1;	   //White has same more number of connections but both have same number of pieces in goal area
		runEvalTests(b4Array, w4Array, result);

		System.out.println("Test 26");
		int[] b5Array = {2, 0, 5, 7, 2, 4};
		int[] w5Array = {0, 2, 7, 5, 4, 2, 4, 5, 3, 6, 6, 3};
		result = 1;	   //White has same more number of connections but both have same number of pieces in goal area
		runEvalTests(b5Array, w5Array, result);
	}

	protected static void runEvalTests(int[] blackCoor, int[] whiteCoor, int expectedOutcome){
		Coordinate[] coors = changeArrays(blackCoor, whiteCoor);
		GameBoard gBoard = new GameBoard(coors);
		double outcome = gBoard.evaluation();

		System.out.println(outcome);
		gBoard.printBoard();
		printCoordinates(coors);
		if(outcome > 0 && expectedOutcome == 1){
			System.out.println("TEST PASSED! MachinePlayer has higher chance of winning!");
		}
		else if (outcome < 0 && expectedOutcome == -1){
			System.out.println("TEST PASSED! Opponent has higher chance of winning!");
		}
		else if (outcome == 0 && expectedOutcome == 0){
			System.out.println("TEST PASSED! Both players have equal chance of winning! Maybe it's an empty gameboard?? /shrug/");
		}
		else{
			System.out.println("TEST FAILED! Outcome does not correspond to the expected outcome!");
		}
	}
} 
