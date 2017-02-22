import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class solve3CNF {
    public static int cliqueSize;
    public static int length;
    public static int clauses;
    public static long timeMS;

    public static int[][] reduce(ArrayList<Integer> cnfTokens, int length) {
		int[][] graph = new int[length][length];
		
		for(int i = 0; i < solve3CNF.length - 1; i++)
		{
			int curr = cnfTokens.get(i);
			for(int j = i + 1; j < solve3CNF.length - 1; j++)
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

	private static boolean ckDuplicates(ArrayList<Integer> temp, int value) {

		for (int i = 0; i < temp.size(); i++) {
			if (temp.get(i) == value) {
				return true;
			}
		}
		return false;
	}
    public static void print(int numGraphs, int cliqueSize, int clauses, ArrayList<Integer> maxClique, ArrayList<Integer> cnf) {

		ArrayList<Integer> aL = new ArrayList<Integer>();
		boolean duplicate = false;
		for (int i = 0; i < maxClique.size(); i++) {
			duplicate = ckDuplicates(aL, cnf.get(maxClique.get(i)));
			if (!duplicate) {
				aL.add(cnf.get(maxClique.get(i)));
			}
		}

		Collections.sort(aL);

		System.out.print("3-CNF No." + numGraphs + ": [n=" + cliqueSize + " k=" + clauses + "] ");

		if (maxClique.size() != clauses) {
			System.out.println("No " + clauses + "-clique; no solution");
		}
		else {
			System.out.print("Assignments: [ ");
			for(int i = 0; i < aL.size(); i++) {
				System.out.print("A" + (i+1) + "=");
				if(aL.get(i) > 0) {

					System.out.print("T ");
				}
				else if(aL.get(i) < 0) {
					System.out.print("F ");
				}
			}
			System.out.println("] (" + timeMS + " ms)");
		}

		aL.clear();
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
                    maxClique = clique.findMaxClique2(cliqueAL, 0 , length, clauses);

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
