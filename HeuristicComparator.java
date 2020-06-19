import java.util.Comparator;

/**
 * Custom comparator that overrides the compare method and looks at total heuristic value.
 * It sorts nodes based on increasing order (Minimal value to the front).
 */
public class HeuristicComparator implements Comparator<Node>{
		
		public int compare(Node a, Node b) 
		{
	        if (a.getHeuristic() < b.getHeuristic()) {
	            return -1;
	        }
	        if (a.getHeuristic() > b.getHeuristic()) {
	            return 1;
	        }
	        return 0;
	    }


	}


