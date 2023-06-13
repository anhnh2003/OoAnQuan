// A class that represents the game board
import java.util.Scanner;
class Board {
    // An array of 12 squares, 10 small and 2 big
    Square[] squares;
  
    // A constructor that creates a new board
    public Board() {
      // Create an array of 12 squares
      squares = new Square[12];
      // Create 10 small squares and assign them an index
      for (int i = 0; i < 10; i++) {
        squares[i] = new SmallSquare(i);
      }
      // Create 2 big squares and assign them a name
      squares[10] = new BigSquare("Left");
      squares[11] = new BigSquare("Right");
    }
  
    // A method to initialize the board with pieces
    public void setup() {
      // Place one big piece or ten small pieces in each big square
      squares[10].addPieces(10);
      squares[11].addPieces(10);
      // Place five small pieces in each small square
      for (int i = 0; i < 10; i++) {
        squares[i].addPieces(5);
      }
    }
  
    // A method to check if the game is over
    public boolean isGameOver() {
      // Count the number of pieces on each side of the board
      int topPieces = 0;
      int bottomPieces = 0;
      for (int i = 0; i < 5; i++) {
        topPieces += squares[i].pieces;
        bottomPieces += squares[i + 5].pieces;
      }
      // The game is over if one side has no pieces left
      return topPieces == 0 || bottomPieces == 0;
    }
  
    // A method to display the board
    public void show() {
      // Print the top row of squares
      System.out.print(squares[11].pieces + " | ");
      for (int i = 0; i < 5; i++) {
        System.out.print(squares[i].pieces + " ");
      }
      System.out.println("| " + squares[10].pieces);
      // Print the bottom row of squares
      System.out.print("   | ");
      for (int i = 5; i < 10; i++) {
        System.out.print(squares[i].pieces + " ");
      }
      System.out.println("|   ");
    }
  }
  
  // An abstract class that represents a square on the board
  abstract class Square {
    // The number of pieces in the square
    int pieces;
  
    // A method to add pieces to the square
    public void addPieces(int n) {
      pieces += n;
    }
  
    // A method to remove all pieces from the square
    public int removeAllPieces() {
      int temp = pieces;
      pieces = 0;
      return temp;
    }
  }
  
  // A subclass that represents a small square on the board
  class SmallSquare extends Square {
    // The index of the square on the board, from 0 to 9
    int index;
  
    // A constructor that assigns an index to the square
    public SmallSquare(int index) {
      this.index = index;
    }
  }
  
  // A subclass that represents a big square on the board
  class BigSquare extends Square {
    // The name of the square, either "Left" or "Right"
    String name;
  
     // A constructor that assigns a name to the square
     public BigSquare(String name) {
       this.name = name;
     }
  }
  
  // A class that represents a player of the game

