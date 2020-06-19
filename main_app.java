import java.util.Scanner;
import java.util.Random;

/**
 * The application is run from this class which contains the main method. It
 * interacts with the human user asking where they would like to place their 
 * line. 
 * Compile with "javac *.java", running the application with "java main_app"
 */
public class main_app {

	/**
	 * Asks the human user for the initial size of the board and the ply
	 * level that the AI will search to using the minmax algorithm.
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int rowSize = 0;
		int colSize = 0;
		int plyLevel = 0;
		int playerScore = 0;
		int aiScore = 0;
		boolean gameFinished = false;
		boolean legalMove = false;
		
		Scanner input = new Scanner(System.in);
		
		System.out.println("Enter the row size for the board: ");
		rowSize = input.nextInt();
		System.out.println("Enter the column size for the board: ");
		colSize = input.nextInt();
		System.out.println("Enter the ply level: ");
		plyLevel = input.nextInt();
		
		// Increasing the user input for the array size so that the correct number of boxes are created
		rowSize = (rowSize * 2) + 1;
		colSize = (colSize * 2) + 1;
		int[][] initialState = new int[rowSize][colSize];
		
		// Fills the initial array with all empty spaces and the random box numbers that can be won
		initialState = gameBoard.initialFill(initialState, rowSize, colSize);
		
		// The first gameboard object created based on the size given by the user
		gameBoard gBoard = new gameBoard(initialState, 0, 0, rowSize, colSize, false);
		
		
		/**
		 * This is the continuous game loop that runs as long as the game is not complete
		 */
		while (!gameFinished)
		{
			int userRow;
			int userCol;
			int line;
			String player = "Human";
			
			// Displays the current board and score for the human user
			gBoard.displayBoard();
			gBoard.showScore();
			System.out.println("Player Move!");
			
			// Displays all available moves
			gBoard.displayMoves();
			System.out.println("");
			System.out.println("Enter the row you want to place the line: ");
			userRow = input.nextInt();
			System.out.println("Enter the column you want to place the line: ");
			userCol = input.nextInt();
	
			// Checks to see if the row/col provided by the user is a legal move. Turns to false and
			// enters the second loop below if it is invalid.
			legalMove = gamePlay.isLegalMove(gBoard.getState(), userRow, userCol, rowSize, colSize);
			
			// Only runs if row/col provided is not a legal move (Already taken, bigger than board size)
			while(!legalMove) 
				{
					gBoard.displayMoves();
					System.out.println("Player Move!");
					System.out.println("Enter the row you want to place the line: ");
					userRow = input.nextInt();
			
					System.out.println("Enter the column you want to place the line: ");
					userCol = input.nextInt();
			
					legalMove = gamePlay.isLegalMove(gBoard.getState(), userRow, userCol, rowSize, colSize);
				}
			
			// Method for checking if the user entered position is for a horizontal or vertical line
			line = gamePlay.lineDirection(userRow, userCol);
			
			// Updates the current gameboard with the move provided
			gBoard.updateGameBoard(line, userRow, userCol);
			
			// Checks to see if a square was made and if the score should be updated.
			gBoard.scoreUpdate(userRow, userCol, line, player);
			
			// Checks to see if all moves have been completed. If so, the while loop will exit.
			gameFinished = gBoard.isFinished();
			
			// If the game isnt over, sets the move to AI. AI takes the board object and completes its turn.
			if (!gameFinished)
			{
				gBoard.moveDolores = true;
				hostAI Dolores = new hostAI(gBoard, plyLevel);
				gBoard = Dolores.play();
			}
			
			legalMove = false;
			gBoard.moveDolores = false;
			gameFinished = gBoard.isFinished();
			
		}
		
		// Sends final results to the helper method to display for the user. 
		gamePlay.finalResults(gBoard);
		
	}

}
