import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
          
        
	        if(isClique){
	        	ArrayList<Integer> currClique = new ArrayList<Integer>(clique);
	        	currClique.add(i);
	        	tempClique = findMaxISet(currClique, i+1, vertices);
	        	
	        	if(tempClique.size() > maxClique.size())
	                  maxClique = tempClique;	
	        }
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

    public static void main(String[] args) {
        solveISet independent = new solveISet();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("graphs16.txt"));
            String line = null;
            while ((line = br.readLine()) != null) {
                vertices = Integer.parseInt(line);

                if (vertices != 0) {
                    numGraphs++;

                    for (int i = 0; i < vertices; i++) {
                        for (int j = 0; j < vertices; j++) {
                            char c = (char) br.read();
                            int v = Character.getNumericValue(c);
                            graph[i][j] = v;
                            numEdges();
                            if (i != j) {
                                if (graph[i][j] == 1) {
                                    graph[i][j] = 0;
                                } else {
                                    graph[i][j] = 1;
                                }
                            }
                            br.read();
                        }
                        br.read();
                        br.read();
                    }
                    ArrayList<Integer> independentAL = new ArrayList<Integer>();
                    independentAL = independent.findMaxISet(independentAL, row, vertices);
                    print(independentAL);
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("File could not be found.");
        } catch (IOException e) {
            System.out.println("File could not be read.");
        }
    }
}
