import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class solve3CNF {
    public static int cliqueSize;
    public static int length;
    public static int clauses;
    public static long timeMS;

    public static int[][] reduce(ArrayList<Integer> cnfTokens, int dimension) {
		int[][] graph = new int[dimension][dimension];
		
		for(int i = 0; i < length; i++)
		{
			int curr = cnfTokens.get(i);
			for(int j = i+1; j < length; j++)
			{
				int bar = cnfTokens.get(j);
				if(i/3 == j/3) continue;
				if(curr != -bar)
				{
					graph[i][j] = 1;
					graph[j][i] = 1;
				}
			}
		}
		return graph;
	}

	private static boolean checkForDuplicates(ArrayList<Integer> temp, int value) {

		for (int i = 0; i < temp.size(); i++) {
			if (temp.get(i) == value) {
				return true;
			}
		}
		return false;
	}
    public static void print(int graphNum, int n, int kClique, ArrayList<Integer> currMaxClique, ArrayList<Integer> cnf) {

		ArrayList<Integer> temp = new ArrayList<Integer>();
		boolean duplicate = false;
		for (int i = 0; i < currMaxClique.size(); i++) {
			duplicate = checkForDuplicates(temp, cnf.get(currMaxClique.get(i)));
			if (!duplicate) {
				temp.add(cnf.get(currMaxClique.get(i)));
			}
		}
		//sort
		int[] sortThese = new int[temp.size()];
		for(int i = 0; i < temp.size(); i++) {
			sortThese[i] = temp.get(i);
		}
		Arrays.sort(sortThese);

		System.out.print("3-CNF No." + graphNum + ": [n=" + n + " k=" + kClique + "] ");

		if (currMaxClique.size() != kClique) {
			System.out.println("No " + kClique + "-clique; no solution");
		}
		else {
			// Print assignments
			System.out.print("Assignments: [ ");
			for(int i = 0; i < sortThese.length; i++) {
				System.out.print("A" + (i+1) + "=");
				if(sortThese[i] > 0) {

					System.out.print("T ");
				}
				else if(sortThese[i] < 0) {
					System.out.print("F ");
				}
			}
			System.out.println("] (" + timeMS + " ms)");
		}

		temp.clear();
	}
    
    public static void main(String[] args){
        solveClique clique = new solveClique();
        ArrayList<Integer> cliqueAL = new ArrayList<>();
        ArrayList<Integer> maxClique = new ArrayList<>();
        ArrayList<Integer> cnf = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("cnfs16.txt"));
            String line = null;

            cliqueSize = 0;
            String[] tokens;
            int num = 0;
            int[][] temp;
            int numGraphs = 1;

            while((line = br.readLine()) != null) {
                long start = System.currentTimeMillis();
                tokens = line.split(" ");
                cliqueSize = Integer.parseInt(tokens[0]);
                if (cliqueSize != 0){
                    length = tokens.length - 1;
                    clauses = length/3;

                    for(int i = 1; i < length; i++){
                        num = Integer.parseInt(tokens[i]);
                        cnf.add(num);
                    }

                    temp = reduce(cnf, length);
                    clique.graph = temp;
                    clique.vertices = length;
                    maxClique = clique.findMaxClique(cliqueAL, 0 , length);

                    print(numGraphs, cliqueSize,clauses, maxClique, cnf);
                    numGraphs++;
                    long end = System.currentTimeMillis();
                    timeMS = end - start;
                    cnf.clear();
                }
            }

        }catch (FileNotFoundException e) {
            System.out.println("File could not be found.");
        }catch (IOException e) {
            System.out.println("File could not be read.");
        }


    }

}
