import java.util.ArrayList;
import java.util.Random;

/**
 * This class is for the gameboard that is used by both the human and AI players. It tracks the state of the game,
 * who has placed what and where. The score of both players, and whose move it is. 
 */
public class gameBoard {
	
	int playerScore;
	int aiScore;
	int[][] state;
	int rowSize;
	int colSize;
	boolean moveDolores;
	
	/**
	 * Constructor for the gameBoard. Takes in a game state, the scores of both the player and AI known as "Dolores", size of
	 * the table, and if it is Dolores move/player move.
	 */
	public gameBoard (int[][] state, int playerScore, int aiScore, int rowSize, int colSize, boolean moveDolores)
	{
		this.state = state;
		this.playerScore = playerScore;
		this.aiScore = aiScore;
		this.rowSize = rowSize;
		this.colSize = colSize;
		this.moveDolores = moveDolores;
	}
	
	/**
	 * Displays the content of the board in text form for the CLI. If the 2D array has a 0, it assumes that is a
	 * empty space to separate squares. If the number is a 6, it is an empty slot for a line. Else it is a horizontal
	 * or vertical line.
	 */
	public void displayBoard ()
	{
			for (int i = 0; i < rowSize; i++ ) 
			{
				for (int j = 0; j < colSize; j++) 
				{
					// Not used in the game. Created so that the squares can be visually seen better
					if (state[i][j] == 0) 
					{
						System.out.print("*" + " ");
					}
					
					// A "6" is for an empty space that can be either a row or column line
					else if (state[i][j] == 6)
					{
						System.out.print(" " + " ");
					}
					
					// A "8" represents a horizontal line 
					else if (state[i][j] == 8) 
					{
						System.out.print("--" + "");
					}
					
					// A "10" represents a vertical line
					else if (state[i][j] == 10) 
					{
						System.out.print("|" + " ");
					}
					
					// Represents the box number that can be claimed during the game
					else 
					{
						System.out.print(state[i][j] + " ");
					}
				}
				
				System.out.println("");
			}
			System.out.println("");
		}
	
	/**
	 * This method fills the initial board in the main method. A random number is created in each of the boxes 
	 * per the assignment notes between 1-5. All of the middle of the boxes are odd numbers and filled based on that condition
	 * in the nested for loops. Else if the 2D array is at a location that is for a line around the box it sets it to 6, which
	 * correlates to empty and waiting for a move.
	 */
	public static int[][] initialFill (int[][] input, int rowSize, int colSize)
	{
		int[][] toSend = input;
		Random rand = new Random();
		
		for (int i = 0; i < rowSize; i++)
		{
			for (int j = 0; j < colSize; j++)
			{
				// All odd row/col combinations will be the center of the boxes will values
				if (i % 2 != 0 && j % 2 != 0)
				{
					int random = rand.nextInt(5 - 1 + 1) + 1;
					toSend[i][j] = random; 
				}
				
				// Rows, i is even
				else if (i % 2 == 0 && j % 2 != 0)
				{
					toSend[i][j] = 6;
				}
				
				// Columns, i is odd
				else if (i % 2 != 0 && j % 2 == 0)
				{
					toSend[i][j] = 6;
				}
			}
		}
		
		return toSend;
	}
	
	/**
	 * Get method to return the state of the board
	 */
	public int[][] getState()
	{
		return this.state;
	}
	
	/**
	 * Method that takes in the line to add, and the location to place it. Updates it state. Used
	 * by both the human and AI player.
	 */
	public void updateGameBoard(int line, int row, int col)
	{
		this.state[row][col] = line;
	}
	
	/**
	 * Method to display all available moves for the human player. Once a move is taken, it is no longer displayed.
	 * The expected display is something like this:
	 * 
	 *  (0, 1) (0, 3) (0, 5) (1, 0) (1, 2) (1, 4) (1, 6) (2, 1) (2, 3) (2, 5) 
	 *	(3, 0) (3, 2) (3, 4) (3, 6) (4, 1) (4, 3) (4, 5) (5, 0) (5, 2) (5, 4) 
	 *	(5, 6) (6, 1) (6, 3) (6, 5) 
	 */
	public void displayMoves()
	{
		int count = 0;
		
		System.out.println("Possible moves: ");
		for (int i = 0; i < this.rowSize; i++) 
		{
			for (int j = 0; j < this.colSize; j++)
			{
				
				// Did this just to make it easier to read for the user
				if (count == 10)
				{
					System.out.println("");
					count = 0;
				}
				
				if (this.state[i][j] == 6)
				{
					System.out.print("(" + i + ", " + j + ") ");
					count++;
				}
			}
		}
	}
	
	/**
	 * Method to update the score based on the last move made by either player. 
	 */
	public void scoreUpdate (int row, int col, int line, String player)
	{
		
		int rowMin2 = row-2;
		int rowMin1 = row-1;
		int rowPlus2 = row+2;
		int rowPlus1 = row+1;
		int colPlus1 = col+1;
		int colPlus2 = col+2;
		int colMin1 = col-1;
		int colMin2 = col-2;
		
		// Horizontal line was placed
		if (line == 8)
		{
			// Last row
			if (row == this.rowSize - 1)
			{
				if (this.state[rowMin2][col] == 8 && this.state[rowMin1][colPlus1] == 10 && this.state[rowMin1][colMin1] == 10)
					{
						if (player.equalsIgnoreCase("Human"))
						{
							playerScore = playerScore + this.state[rowMin1][col];
						}
						
						else if (player.equalsIgnoreCase("Dolores"))
						{
							aiScore = aiScore + this.state[rowMin1][col];
						}
					}
			}
			
			// First row
			if (row == 0)
			{
				if (this.state[rowPlus2][col] == 8 && this.state[rowPlus1][colPlus1] == 10 && this.state[rowPlus1][colMin1] == 10)
				{
					if (player.equalsIgnoreCase("Human"))
					{
						playerScore = playerScore + this.state[rowPlus1][col];
					}
					
					else if (player.equalsIgnoreCase("Dolores"))
					{
						aiScore = aiScore + this.state[rowPlus1][col];
					}
				}
			}
			
			// Middle of Board
			if (row != 0 && row != this.rowSize-1)
			{
				if (this.state[rowPlus2][col] == 8 && this.state[rowPlus1][colPlus1] == 10 && this.state[rowPlus1][colMin1] == 10)
				{
					if (player.equalsIgnoreCase("Human"))
					{
						playerScore = playerScore + this.state[rowPlus1][col];
					}
					
					else if (player.equalsIgnoreCase("Dolores"))
					{
						aiScore = aiScore + this.state[rowPlus1][col];
					}
				}
				
				else if (this.state[rowMin2][col] == 8 && this.state[rowMin1][colPlus1] == 10 && this.state[rowMin1][colMin1] == 10)
				{
					if (player.equalsIgnoreCase("Human"))
					{
						playerScore = playerScore + this.state[rowMin1][col];
					}
					
					else if (player.equalsIgnoreCase("Dolores"))
					{
						aiScore = aiScore + this.state[rowMin1][col];
					}
				}
			}
		
		}
		
		// Vertical column was added
		if (line == 10)
		{
			// Last column
			if (col == this.colSize - 1)
			{
				if (this.state[rowPlus1][colMin1] == 8 && this.state[row][colMin2] == 10 && this.state[rowMin1][colMin1] == 8)
					{
						if (player.equalsIgnoreCase("Human"))
						{
							playerScore = playerScore + this.state[row][colMin1];
						}
						
						else if (player.equalsIgnoreCase("Dolores"))
						{
							aiScore = aiScore + this.state[row][colMin1];
						}
					}
			}
			
			// First column
						if (col == 0)
						{
							if (this.state[rowPlus1][colPlus1] == 8 && this.state[row][colPlus2] == 10 && this.state[rowMin1][colPlus1] == 8)
								{
									if (player.equalsIgnoreCase("Human"))
									{
										playerScore = playerScore + this.state[row][colPlus1];
									}
									
									else if (player.equalsIgnoreCase("Dolores"))
									{
										aiScore = aiScore + this.state[row][colPlus1];
									}
								}
						}
						
			// Middle of board
						if (col != 0 && col != this.colSize-1)
						{
							if (this.state[rowPlus1][colPlus1] == 8 && this.state[row][colPlus2] == 10 && this.state[rowMin1][colPlus1] == 8)
								{
									if (player.equalsIgnoreCase("Human"))
									{
										playerScore = playerScore + this.state[row][colPlus1];
									}
									
									else if (player.equalsIgnoreCase("Dolores"))
									{
										aiScore = aiScore + this.state[row][colPlus1];
									}
								}
							
							else if (this.state[rowPlus1][colMin1] == 8 && this.state[row][colMin2] == 10 && this.state[rowMin1][colMin1] == 8)
								{
									if (player.equalsIgnoreCase("Human"))
									{
										playerScore = playerScore + this.state[row][colMin1];
									}
									
									else if (player.equalsIgnoreCase("Dolores"))
									{
										aiScore = aiScore + this.state[row][colMin1];
									}
								}
						}
		}
	}
	
	/**
	 * Method to display score for both players
	 */
	public void showScore()
	{
		System.out.println("Human Score: " + playerScore);
		System.out.println("AI Score: " + aiScore);
	}
	
	/**
	 * Method for determining if the game has been completed or not. If all squares are not
	 * set to 6, then the game has finished and it returns a true value to the main_app.
	 */
	public boolean isFinished()
	{
		boolean toSend = true;
		
		for (int i = 0; i < this.rowSize; i++) 
		{
			for (int j = 0; j < this.colSize; j++)
			{
				if (this.state[i][j] == 6)
				{
					toSend = false;
				}
				
			}
		}
		
		return toSend;
	}
	
	
	
}
