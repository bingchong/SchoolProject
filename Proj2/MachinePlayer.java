/* MachinePlayer.java */

package player;
import list.*;
import java.util.Random;


/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 */

public class MachinePlayer extends Player {
	protected int myColor;
	protected int opponentColor;
	protected int depth;
	protected int stepDepth;
	protected GameBoard board;


	/**Constructor for MachinePlayer that takes player’s color.
	 *Initialize the opponentColor.
	 *Sets the default search depth of 3.
	 *Sets stepDepth to 1.
	 *Creates the internal GameBoard.
	 *(White has the first move.)
	 */
	public MachinePlayer(int color) {
		myColor = color;
		opponentColor = Math.abs(color-1);
		board = new GameBoard();
		depth = 3;  // returns a move within five seconds
		stepDepth = 1;
	}

	/**Constructor for MachinePlayer that takes player’s color and searchDepth.
	 *Assign specific searchDepth.
	 *Sets stepDepth to searchDepth.
	 */
	public MachinePlayer(int color, int searchDepth) {
		this(color);
		depth = stepDepth = searchDepth;
	}

	/**Returns a new move by machine player.
	 *If the machine player make the first move, it place its color specific places. (WHITE: (0,3) BLACK:(3,0))
	 *Calls the minimax function to evaluate the best move among valid moves .
	 *When minimax is initially called, alpha is -1 and beta is 1.
	 *Updates machine player’s move internal board.
	 */
	public Move chooseMove() {
		BestMove m = new BestMove();
		minimaxCalls: {
			if (board.isEmpty && myColor == GameBoard.WHITE){
				m.bestMove = new Move(0, 3);
			} else if (board.totalNumPieces() == 1 && myColor == GameBoard.BLACK){
				m.bestMove = new Move(3,0);

			}else {
				if (board.totalNumPieces() == 20){
					m = minimax(board, myColor, -1, 1, stepDepth);
					break minimaxCalls;
				}
				for (int n = 1; n <= depth; n++){
					m = minimax(board, myColor, -1,1, n);
					if (m.score == Math.abs(1)){
						break minimaxCalls;
					}
				}

			}
		}
		board.updateBoard(m.bestMove, myColor);
		return m.bestMove;

	}

	// If the Move m is legal, records the move as a move by the opponent
	// (updates the internal game board) and returns true.  If the move is
	// illegal, returns false without modifying the internal state of "this"
	// player.  This method allows your opponents to inform you of their moves.
	public boolean opponentMove(Move m) {
		if(board.isValidMove(m, opponentColor)){
			board.updateBoard(m,opponentColor);
			return true;
		}
		else
			return false;
	}

	// If the Move m is legal, records the move as a move by "this" player
	// (updates the internal game board) and returns true.  If the move is
	// illegal, returns false without modifying the internal state of "this"
	// player.  This method is used to help set up "Network problems" for your
	// player to solve.
	public boolean forceMove(Move m) {
		if (board.isValidMove(m, myColor)){
			board.updateBoard(m, myColor);
			return true;
		}
		else
			return false;
	}

	/**Takes currentBoard, color, alpha, beta, and searchDepth.
	 *Returns the BestMove object which contains score and bestMove.  
	 *BestMove object is determined by implementing game tree search and alpha beta pruning.
	 */
	private BestMove minimax(GameBoard currentBoard, int color, double alpha, double beta,  int searchDepth){

		//myBest stores my best move & score      
		BestMove myBest = new BestMove();

		//reply stores opponent move & score
		BestMove reply;

		// this keeps track of moves that can be made
		List moveList = currentBoard.validMoves(color);
		ListNode move = moveList.front();

		//base case for recursion
		// if the current board has a hasANetwork or searchDepth 
		if (board.hasANetwork(color) || searchDepth == 0){       
			if(myColor == GameBoard.WHITE){                
				myBest.score = currentBoard.evaluation();
			}
			else{
				myBest.score = currentBoard.evaluation() * -1;
			}            
			return myBest;
		}
		if(color == myColor){
			myBest.score = alpha;                  
		}else{
			myBest.score = beta;
		}
		try{
			myBest.bestMove = (Move) move.item();
			while(move.isValidNode()){
				GameBoard nBoard = currentBoard.copy();
				nBoard.updateBoard((Move)move.item(),color);            
				reply = minimax(nBoard, Math.abs(color-1),alpha, beta,  searchDepth-1);                
				if ((color == myColor) && (reply.score > myBest.score)){ 
					myBest.bestMove = (Move)move.item();
					myBest.score = reply.score;
					alpha = reply.score;                       
				}else if ((color != myColor) && (reply.score < myBest.score)){
					myBest.bestMove = (Move)move.item();
					myBest.score = reply.score;
					beta = reply.score;
				}                
				if (alpha >= beta){ 
					return myBest;
				}                
				move = move.next();
			}       
		}catch(InvalidNodeException e){
			e.printStackTrace();
		}finally{
			return myBest;
		}
	}
}
