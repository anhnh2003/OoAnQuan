// Import the Swing library
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// A class that represents the game board as a panel
// A class that represents the game board as a panel
class BoardPanel extends JPanel {
    // An array of 12 buttons, 10 small and 2 big
    JButton[] buttons;
    // A board object that represents the game logic
    Board board;
  
    // A constructor that creates a new board panel
    public BoardPanel() {
      // Set the layout of the panel as a grid with two rows and six columns
      setLayout(new GridLayout(2, 6));
      // Create an array of 12 buttons
      buttons = new JButton[12];
      // Create 10 small buttons and assign them an index as their text
      for (int i = 0; i < 10; i++) {
        buttons[i] = new JButton(String.valueOf(i));
      }
      // Create 2 big buttons and assign them a name as their text
      buttons[10] = new JButton("Left");
      buttons[11] = new JButton("Right");
      // Add the buttons to the panel in reverse order
      for (int i = 0; i <= 11; i++) {
        add(buttons[i]);
      }
      // Create a board object and initialize it with pieces
      board = new Board();
      setup();
    }
  
    // A method to initialize the board with pieces
    public void setup() {
      // Place one big piece or ten small pieces in each big button
      buttons[10].setText("Left (" + board.squares[10].pieces + ")");
      buttons[11].setText("Right (" + board.squares[11].pieces + ")");
      // Place five small pieces in each small button
      for (int i = 0; i < 10; i++) {
        buttons[i].setText(i + " (" + board.squares[i].pieces + ")");
      }
    }
  
    // A method to get the number of pieces in a square
    public int getPieces(int index) {
      // Get the number of pieces from the board object
      return board.squares[index].pieces;
    }
  
    // A method to set the number of pieces in a square
    public void setPieces(int index, int n) {
      // Set the number of pieces in the board object
      board.squares[index].pieces = n;
      // Get the text of the button
      String text = buttons[index].getText();
      // Split the text by space and get the first part
      String[] parts = text.split(" ");
      String first = parts[0];
      // Add the number in parentheses and set the text of the button
      text = first + " (" + n + ")";
      buttons[index].setText(text);
    }
  
    // A method to add an action listener to all buttons
    public void addActionListener(ActionListener listener) {
      for (int i = 0; i < 12; i++) {
        buttons[i].addActionListener(listener);
      }
    }
  
     // A method to disable all buttons
     public void disableAllButtons() {
       for (int i = 0; i < 12; i++) {
         buttons[i].setEnabled(false);
       }
     }
  
     // A method to check if the game is over
     public boolean isGameOver() {
       return board.isGameOver();
     }
  }

// A class that represents a player of the game as a panel
class PlayerPanel extends JPanel {
   // The name of the player as a label
   JLabel nameLabel;
   // The side of the board that the player controls, either "Top" or "Bottom"
   String side;
   // The score of the player as a label
   JLabel scoreLabel;

   // A constructor that creates a new player panel with a given name and side
   public PlayerPanel(String name, String side) {
     // Set the layout of the panel as a grid with one row and two columns
     setLayout(new GridLayout(1, 2));
     // Create a label with the name of the player and add it to the panel
     nameLabel = new JLabel(name);
     add(nameLabel);
     // Store the side of the player
     this.side = side;
     // Create a label with the score of the player and add it to the panel
     scoreLabel = new JLabel("Score: 0");
     add(scoreLabel);
   }

   // A method to get the score of the player
   public int getScore() {
     // Get the text of the score label
     String text = scoreLabel.getText();
     // Split the text by space and get the last part
     String[] parts = text.split(" ");
     String last = parts[parts.length - 1];
     // Parse and return the number
     return Integer.parseInt(last);
   }

   // A method to set the score of the player
   public void setScore(int n) {
     // Set the text of the score label with the new score
     scoreLabel.setText("Score: " + n);
   }
}

// A class that runs the game as a frame
class GameFrame extends JFrame implements ActionListener {
  // A panel that contains the board
  BoardPanel boardPanel;
  // A panel that contains the two players
  JPanel playerPanel;
  // The first player
  PlayerPanel player1;
  // The second player
  PlayerPanel player2;
  // The current player
  PlayerPanel currentPlayer;

  // A constructor that creates a new game frame
  public GameFrame() {
    // Set the title of the frame
    setTitle("Ô ăn quan");
    // Set the size of the frame
    setSize(600, 400);
    // Set the layout of the frame as a border layout
    setLayout(new BorderLayout());
    // Create a board panel and add it to the center of the frame
    boardPanel = new BoardPanel();
    add(boardPanel, BorderLayout.CENTER);
    // Create a player panel and add it to the south of the frame
    playerPanel = new JPanel();
    add(playerPanel, BorderLayout.SOUTH);
    // Create two players with their names and sides and add them to the player panel
    player1 = new PlayerPanel("Alice", "Top");
    player2 = new PlayerPanel("Bob", "Bottom");
    playerPanel.add(player1);
    playerPanel.add(player2);
    // Add an action listener to all buttons on the board
    boardPanel.addActionListener(this);
    // Set the current player as player1
    currentPlayer = player1;
    // Set the default close operation of the frame to exit on close
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // Set the visibility of the frame to true
    setVisible(true);
  }

  // A method to handle button clicks
  public void actionPerformed(ActionEvent e) {
     JButton button = (JButton) e.getSource();
     String text = button.getText();
     String[] parts = text.split(" ");
     String first = parts[0];
     if (first.matches("\\d+")) {
       int index = Integer.parseInt(first);
       if (currentPlayer.side.equals("Top") && index > 4) {
         JOptionPane.showMessageDialog(this, "Invalid choice. Please choose a square from your side (" + currentPlayer.side + ").");
         return;
       }
       if (currentPlayer.side.equals("Bottom") && index < 5) {
         JOptionPane.showMessageDialog(this, "Invalid choice. Please choose a square from your side (" + currentPlayer.side + ").");
         return;
       }
       // Get the number of pieces in the square and check if it is zero
       int pieces = boardPanel.getPieces(index);
       if (pieces == 0) {
         JOptionPane.showMessageDialog(this, "Empty square. Please choose another square.");
         return;
       }
       boardPanel.setPieces(index, 0);
              String direction = JOptionPane.showInputDialog(this, currentPlayer.getName() + ", do you want to scatter the pieces clockwise or counterclockwise? (C for clockwise, CC for counterclockwise)");
              if (!direction.equals("C") && !direction.equals("CC")) {
                JOptionPane.showMessageDialog(this, "Invalid direction. Please enter C or CC.");
                return;
              }
              while (pieces > 0) {
                // Move to the next square according to the direction
                if (direction.equals("C")) {
                  index = (index + 1) % 12;
                } else {
                  index = (index - 1 + 12) % 12;
                }
                boardPanel.setPieces(index, boardPanel.getPieces(index) + 1);
                pieces--;
              }
              if (boardPanel.getPieces(index) % 2 == 0 && ((currentPlayer.side.equals("Top") && index > 4 && index < 10) || (currentPlayer.side.equals("Bottom") && index < 5))) {
                currentPlayer.setScore(currentPlayer.getScore() + boardPanel.getPieces(index));
                boardPanel.setPieces(index, 0);
                JOptionPane.showMessageDialog(this, currentPlayer.getName() + ", you captured " + currentPlayer.getScore() + " pieces from the last square!");
              }
              if (boardPanel.getPieces(index) > 1 && (index == 10 || index == 11)) {
                currentPlayer.setScore(currentPlayer.getScore() + boardPanel.getPieces(index) - 1);
                boardPanel.setPieces(index, 1);
                JOptionPane.showMessageDialog(this, currentPlayer.getName() + ", you captured " + currentPlayer.getScore() + " pieces from the last square!");
              }
              if (boardPanel.isGameOver()) {
                if (player1.getScore() > player2.getScore()) {
                  JOptionPane.showMessageDialog(this, player1.getName() + " wins!");
                } else if (player1.getScore() < player2.getScore()) {
                  JOptionPane.showMessageDialog(this, player2.getName() + " wins!");
                } else {
                  JOptionPane.showMessageDialog(this, "It's a tie!");
                }
                boardPanel.disableAllButtons();
              } else {
                // Switch the current player
                if (currentPlayer == player1) {
                  currentPlayer = player2;
                } else {
                  currentPlayer = player1;
                }
              }
            } else {
              // Ignore clicks on big squares
              JOptionPane.showMessageDialog(this, "Please choose a small square.");
            }
          }
       
          // A main method that creates and runs a new game frame
          public static void main(String[] args) throws Exception{
             new GameFrame();
          }
       }