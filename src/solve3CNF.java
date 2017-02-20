import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class solve3CNF {
    public static int cliqueSize;
    public static int length;
    public static int clauses;
    public static long timeMS;

    public static void main(String[] args){
        solveClique clique = new solveClique();
        ArrayList<Integer> cliqueAL = new ArrayList<>();
        ArrayList<Integer> maxClique = new ArrayList<>();
        ArrayList<Integer> cnf = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("cnfs.txt"));
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
