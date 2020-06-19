import java.util.ArrayList;

/**
 * Node class for holding gameboard objects and providing get and set methods for parents,
 * and children of the node. It also has a depth attribute, which is used during the minmax algorithm
 * to make sure the ply entered by the user is not exceeded.
 */
public class Node {
	
	gameBoard gBoard;
	Node parent;
	ArrayList<Node> Children = new ArrayList<Node>();
	int depth;
	int heuristic;
	
	/**
	 * Constructor for Node takes in the gameboard object
	 */
	public Node(gameBoard g)
	{
		this.gBoard = g;
	}
	
	/**
	 * Return gameboard object from constructor
	 */
	public gameBoard getBoard()
	{
		return gBoard;
	}

	/**
	 * Set method for parent of the current node
	 */
	public void setParent(Node parent)
	{
		this.parent = parent;
	}
	
	/**
	 * Get method for parent of the current node
	 */
	public Node getParent()
	{
		return parent;
	}
	
	/**
	 * Set method for depth of the current node
	 */
	public void setDepth(int depth)
	{
		this.depth = depth;
	}
	
	/**
	 * Get method for the depth of the current node
	 */
	public int getDepth()
	{
		return depth;
	}
	
	/**
	 * Method for adding children nodes to the ArrayList
	 */
	public void addNino(Node child)
	{
		Children.add(child);
	}
	
	/**
	 * Method for setting the heuristic used in the minmax algorithm. The acutal computation
	 * takes place in the hostAI class.
	 */
	public void setHeuristic(int input)
	{
		this.heuristic = input;
	}
	
	/**
	 * Get method for returning the heuristic value computed
	 */
	public int getHeuristic()
	{
		return heuristic;
	}
}
