import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class solveISet {
    public static int[][] graph;
    public static int edges, vertices, numGraphs, row;
    public static long timeMS;

    public solveISet() {
        graph = new int[60][60];
        numGraphs = 0;
        edges = 0;
        vertices = 0;
        row = 0;
        timeMS = 0;
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
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not find file.");
        } catch (IOException e) {
            System.out.println("Could not read file.");
        }
    }
}
