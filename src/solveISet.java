package src;
import java.util.ArrayList;

public class solveISet {
	
	public static int[][] graph;
	public static int vertices, numGraphs, edges, row;
	public static long timeMS;

	/**
	 * Default Constructor
	 */
	public solveISet() {
		graph = new int[60][60];
		vertices = 0;
		numGraphs = 0;
		edges = 0;
		row = 0;
		timeMS = 0;
	}
	public ArrayList<Integer> findMaxISet(ArrayList<Integer> clique, int row, int dim)
	{
		long start = System.currentTimeMillis();
		
		ArrayList<Integer> tempClique = new ArrayList<Integer>();
		ArrayList<Integer> maxClique = new ArrayList<Integer>();
		maxClique = clique;
		
		for(int i = row; i < dim; i++)
		{
			boolean isClique = true;
			for(int j = 0; j < clique.size(); j++)
				if(graph[clique.get(j)][i] != 1) isClique = false;
			if(tempClique.size() > maxClique.size())
				maxClique = tempClique;
		}
		
		long end = System.currentTimeMillis();
		timeMS = end - start;
		
		return maxClique;
	}
	private static int numEdges(){
		edges = 0;
		for(int i = 0; i < vertices; i++)
		{
			for(int j = i; j < vertices; j++)
			{
				if(graph[i][j] == 1)
					edges++;
			}
		}
		return edges;
	}
	private static void print(ArrayList<Integer> clique)
	{
		int size = clique.size();
		
		System.out.println("G" + numGraphs + " (" + vertices + ", " + edges + ") " + clique.toString() + "(size=" + clique.size() + ", " + timeMS + " ms)");
	}
}
