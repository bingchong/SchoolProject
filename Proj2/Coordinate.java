/* Coordinate.java */
//Names: Bing Chong Lim   (cs61b-gw) 
//		 Joy Jeng		  (cs61b-gu)
//		 Jin Myung Kwak	  (cs61b-xv)
package player;

/**
 *  A representation of a piece on the board, with color and coordinate attributes.
 */
public class Coordinate {  
  public int x, y, color, number;
  protected final static int BLACK = 0;
  protected final static int WHITE = 1;
  protected final static int EMPTY = 10;
  
  //Constructor for the Coordinate class
  //Initializes the Coordinate object by storing the coordinate and color of a particular piece
  public Coordinate (int x, int y, int color) {
    this.x = x;
    this.y = y;
    this.color = color;
  }
  
  //returns a String representation of the Coordinate object
  public String toString() {
    return color() + "(" + x + ", " + y + ") ";
  }
  
  //returns a String representation of the color of the piece that the Coordinate object is holding
  public String color() {
    if (color == BLACK) {
      return "black";
    } else if (color == WHITE) {
      return "white";
    } else {
      return "empty";
    }
  }
  
  //Compares the information stored inside two Coordinate objects and determines whether the two Coordinate objects are the same
  public boolean equals(Coordinate c) {
    if (x == c.x && y == c.y && color == c.color) {
      return true;
    }
    return false;
  }
}