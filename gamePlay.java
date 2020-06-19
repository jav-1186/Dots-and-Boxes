

/**
 * This class provides some helper methods for the main_app and hostAI class
 */
public class gamePlay {
	
	/**
	 * Checks in the current state of the game board if the selected row and column are empty. 
	 * When doing the initial fill, all empty slots are set to 6. So if the int in that slot is a 
	 * 6 it is a legal move and it will return true, else false.
	 */
	public static boolean isLegalMove(int[][] state, int row, int col, int rowSize, int colSize)
	{
		// Added this method for a corner case where if the user enters a row or column larger then board it returns a false move
		if (row > rowSize || col > colSize)
		{
			System.out.println("Board size is smaller than entered values");
			return false;
		}
		
		// Normal use case, checks if the board spot is empty
		else if (state[row][col] != 6)
		{
			System.out.println("Spot already has a line, try again!");
			return false;
		}
		
		return true;
	}

	/**
	 * Method to determine if the desired space is either horizontal or vertical. If the row is even,
	 * it has to be horizontal. If it is vertical it will be odd.
	 */
	public static int lineDirection (int row, int col)
	{
		int toSend = 0;
		
		// Horizontal line
		if (row % 2 == 0)
		{
			toSend = 8;
		}
		
		// Vertical column
		else if (row % 2 != 0)
		{
			toSend = 10;
		}
		
		return toSend;
	}
	
	/**
	 * Method that will display the final results to the user. Checks the score of both players
	 * from the gameboard and computes the winner.
	 */
	public static void finalResults(gameBoard gBoard)
	{
		System.out.println("Game Complete!!");
		System.out.println("Human Score: " + gBoard.playerScore);
		System.out.println("AI Score: " + gBoard.aiScore);
		
		if(gBoard.playerScore > gBoard.aiScore)
		{
			System.out.println("Human Player wins!");
		}
		
		else if (gBoard.playerScore == gBoard.aiScore)
		{
			System.out.println("It'ssssss a tie!!!");
		}
		
		else
			System.out.println("AI Wins!!");
	}
}
