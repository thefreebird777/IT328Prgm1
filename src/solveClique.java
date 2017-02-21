
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class solveClique {
    public static int[][] graph;
    public static int edges, vertices, numGraphs, row;
    public static long timeMS;

    public solveClique() {
        graph = new int[60][60];
        numGraphs = 0;
        edges = 0;
        vertices = 0;
        row = 0;
        timeMS = 0;
    }

    public ArrayList<Integer> findMaxClique(ArrayList<Integer> clique, int row, int vertices) {
        long start = System.currentTimeMillis();

        ArrayList<Integer> tempClique = new ArrayList<Integer>();
        ArrayList<Integer> maxClique = new ArrayList<Integer>();

        maxClique = clique;

        for (int i = row; i < vertices; i++) {
            boolean isClique = true;
            for (int j = 0; j < clique.size(); j++) {
                if (graph[clique.get(j)][i] != 1) {
                    isClique = false;
                }
            }

            if (isClique) {
                ArrayList<Integer> currClique = new ArrayList<Integer>(clique);
                currClique.add(i);
                tempClique = findMaxClique(currClique, i + 1, vertices);

                if (tempClique.size() > maxClique.size())
                    maxClique = tempClique;
            }
        }
        long end = System.currentTimeMillis();
        timeMS = end - start;
        return maxClique;
    }

    private static int numEdges() {
        edges = 0;
        for (int i = 0; i < vertices; i++) {
            for (int j = i; j < vertices; j++) {
                if (graph[i][j] == 1)
                    edges++;
            }
        }
        return edges;
    }

    private static void print(ArrayList<Integer> clique) {
        int size = clique.size();

        System.out.println("G" + numGraphs + " (" + vertices + ", " + edges + ") " + clique.toString() + "(size=" + clique.size() + ", " + timeMS + " ms)");
    }

    public static void main(String[] args) {
        solveClique clique = new solveClique();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("graphs16.txt"));
            String line = null;
            while ((line = br.readLine()) != null) {
                edges = 0;
                vertices = Integer.parseInt(line);

                if (vertices != 0) {
                    numGraphs++;

                    for (int i = 0; i < vertices; i++) {
                        for (int j = 0; j < vertices; j++) {
                            char c = (char) br.read();
                            int v = Character.getNumericValue(c);
                            graph[i][j] = v;
                            numEdges();
                            br.read(); //space
                        }
                        br.read(); //newline
                        br.read(); //newline
                    }

                    ArrayList<Integer> cliqueAL = new ArrayList<Integer>();
                    cliqueAL = clique.findMaxClique(cliqueAL, row, vertices);
                    print(cliqueAL);
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not find file.");
        } catch (IOException e) {
            System.out.println("Could not read file.");
        }
    }

}

