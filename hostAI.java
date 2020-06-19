import java.util.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.PriorityQueue;

/**
 * This class performs all functions for the AI user, known as "Dolores" (Reference to the host
 * in Westworld on HBO). It also performs the minmax and pruning algorithm.
 */
public class hostAI {
	
	gameBoard gBoard;
	int ply;
	
	/**
	 * Constructor takes in the gameboard and ply level from the main app
	 */
	public hostAI (gameBoard gBoard, int ply)
	{
		this.gBoard = gBoard;
		this.ply = ply;
	}
	
	/**
	 * Method to run Dolores turn, and run through the minmax algorithm. Returns the gameboard to the human user
	 * from the method call in the main_app class. This method uses a Priority Queue to store the nodes which
	 * is based on a heuristic calculation. I did this to improve upon node ordering and try to speed up
	 * which Nodes are pruned. The intention is that it searches Nodes first that seem more promising.
	 */
	public gameBoard play()
	{
		Node start = new Node(gBoard);
		int maxScore = Integer.MAX_VALUE;
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		Node best = null;
		
		HeuristicComparator heuristic_comparator = new HeuristicComparator();
		PriorityQueue<Node> pq = new PriorityQueue<Node>(12, heuristic_comparator);
		pq = findSuccessors(start, false);
		
		for (int i = 0; i < pq.size(); i++)
		{
			Node child = pq.poll();
			
			int eval = minMax(child, ply, 0, alpha, beta, true);
			
				if (eval < maxScore)
				{
					maxScore = eval;
					best = child;
				}
		}
		return best.gBoard;
	}
	
	
	/**
	 * Method for finding all the successor moves of a node. They are added to the nodes ArrayList as children
	 * and it sets the parent of the child node to the current node going through.
	 */
	public PriorityQueue<Node> findSuccessors (Node node, boolean moveDolores)
	{
		gameBoard gBoard = node.getBoard();
		//ArrayList<Node> Ninos = new ArrayList<Node>();
		HeuristicComparator heuristic_comparator = new HeuristicComparator();
		PriorityQueue<Node> pq = new PriorityQueue<Node>(12, heuristic_comparator);
		
		int[][] boardState = gBoard.getState();
		
		for (int i = 0; i < gBoard.rowSize; i++)
		{
			for (int j = 0; j < gBoard.colSize; j++)
			{
				// This is for rows
				if (i % 2 == 0 && boardState[i][j] == 6)
				{
					// Creating a copy of the state
					int[][] child = clone(boardState, gBoard.rowSize, gBoard.colSize);
					child[i][j] = 8;
					
					// Creates new gameboard object 
					gameBoard childBoard = new gameBoard(child, gBoard.playerScore, gBoard.aiScore, gBoard.rowSize, gBoard.colSize, moveDolores);
					
					// Find type of move, updating gameboard, and checking if a score was made for Dolores
					int line = gamePlay.lineDirection(i, j);
					childBoard.updateGameBoard(line, i, j);
					childBoard.scoreUpdate(i, j, line, "Dolores");
				
					// Creating the child node, setting its parent to the current node and then adding it to the array of the node
					Node nino = new Node(childBoard);
					
					// Used for improved heuristic ordering in the PQ. Not for minmax 
					nino.setHeuristic(nino.getBoard().playerScore - nino.getBoard().aiScore);
					nino.setParent(node);
					//Ninos.add(nino);
					pq.add(nino);
					
				}
				
				// This is for columns
				else if (i % 2 != 0 && boardState[i][j] == 6)
				{
					int[][] child = clone(boardState, gBoard.rowSize, gBoard.colSize);
					child[i][j] = 10;
					
					gameBoard childBoard = new gameBoard(child, gBoard.playerScore, gBoard.aiScore, gBoard.rowSize, gBoard.colSize, false);
					
					int line = gamePlay.lineDirection(i, j);
					childBoard.updateGameBoard(line, i, j);
					childBoard.scoreUpdate(i, j, line, "Dolores");

					Node nino = new Node(childBoard);
					nino.setParent(node);
					//Ninos.add(nino);
					pq.add(nino);
					
				}
			}
		}
		
		return pq;
	}
	
	/**
	 * Helper method to take in the current state and copy it. It must copy the original state so that only 
	 * one move is made on the board by the AI. 
	 */
	public int[][] clone (int[][] input, int rowSize, int colSize)
	{
		int[][] toSend = new int[rowSize][colSize];
		
		for (int i = 0; i < rowSize; i++)
		{
			for (int j = 0; j < colSize; j++)
			{
				toSend[i][j] = input[i][j];
			}
		}
		
		return toSend;
	}
	
	/**
	 * Algorithm to traverse down the tree and find the possible moves. Finds children of the current node
	 * and cycles back and forth between "Min" and "Max". A heuristic is used and calculated to determine the best
	 * possible move at the leaf node.
	 */
	public int  minMax (Node running, int ply, int currentDepth, int alpha, int beta, boolean max)
	{
		HeuristicComparator heuristic_comparator = new HeuristicComparator();
		PriorityQueue<Node> pq = new PriorityQueue<Node>(12, heuristic_comparator);
		pq = findSuccessors(running, max);
		
		// Leaf node reached or game is over. Heuristic function applied to leaf node
		if (running.getBoard().isFinished() == true || ply == 0)
		{
			int heuristic = running.getBoard().playerScore - running.getBoard().aiScore;
			return heuristic;
		}
		
		// AI move or "Min"
		if (!max)
		{
			for (int i = 0; i < pq.size(); i++)
			{
				Node child = pq.poll();
				beta = Math.min(beta, minMax(child, ply-1, 0, alpha, beta, true));
			
				// Pruning 
				if (beta <= alpha)
				{
					break;
				}
			}
			
			return beta;
		}
		
		// Human or "Max" move
		else
		{
			for (int i = 0; i < pq.size(); i++)
			{
				Node child = pq.poll();
				alpha = Math.max(alpha, minMax(child, ply-1, 0, alpha, beta, false));

				// Pruning
				if (beta <= alpha)
				{
					break;
				}
			}
			
			return alpha;
		}
		
	}

}
