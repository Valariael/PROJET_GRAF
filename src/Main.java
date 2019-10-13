import java.io.*;
import java.util.Arrays;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {

        Graf g1 = new Graf(2,4,0,0,6,0,2,3,5,8,0,0,4,7,0,3,0,7);
        printGraf(g1);
        System.out.println();
        try {
            g1.toDotFile("test.dot");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Graf g2 = getGrafFromDotFile("example.dot");
            printGraf(g2);
            System.out.println();
            System.out.println("last graph's BFS : " + g2.getBFS(null).toString());
            System.out.println();
            System.out.println("last graph's DFS : " + g2.getDFS(null).toString());
            System.out.println();
            int[][] matrix = g2.getAdjMatrix();
            for(int i = 0; i < matrix.length-1; i++) {
                for(int j = 0; j < matrix[i].length-1; j++) {
                    System.out.print(matrix[i][j]);
                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        UndirectedGraf g3 = UndirectedGraf.randomGrafBuilder(5, 0.5, true);
        System.out.println();
        printGraf(g3);
        System.out.println();
        System.out.println(Arrays.toString(g3.getSuccessorArray()));
        try {
            g3.toDotFile("random_connected_graf_5.dot");
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void printGraf(Graf g) {
        System.out.println("printing graph :");
        g.adjList.forEach((key, value) -> System.out.println(key + " " + value.toString()));
    }

    public static Graf getGrafFromDotFile(String pathToFile) throws IOException
    {
        File file = new File(pathToFile);
        BufferedReader br = new BufferedReader(new FileReader(file));

        Graf g = null;
        String s;
        Node from, to, keyFrom, keyTo;
        String[] parts;

        while ((s = br.readLine()) != null) {
            if(s.length() <= 2) System.out.println("> empty line read");
            else if(s.contains("#")) System.out.println("> commented line read");
            else if(s.contains("digraph")) g = new Graf();
            else if(s.contains("graph")) g = new UndirectedGraf();
            else {
                parts = s.split(" ");
                int start = 0;
                if(s.startsWith(" ")) start = 1;
                from = new Node(Integer.parseInt(parts[start]));
                keyFrom = Objects.requireNonNull(g).getKeyFromGraf(from); // satisfying IDE

                if(parts.length <= start + 3) {
                    to = new Node(Integer.parseInt(parts[start + 2].substring(0,parts[start + 2].length()-1)));
                } else {
                    int toWeight = Integer.parseInt(parts[start + 3].substring(8,parts[start + 3].length()-2));
                    to = new Node(Integer.parseInt(parts[start + 2]), toWeight);
                }
                keyTo = g.getKeyFromGraf(to);

                if(keyFrom == null) {
                    g.addNode(from);
                    keyFrom = from;
                }
                if(keyTo == null) {
                    g.addNode(to);
                    keyTo = to;
                }

                g.addEdge(keyFrom, keyTo);
                if(g instanceof UndirectedGraf) {
                    g.addEdge(keyTo, keyFrom);
                }
            }
        }

        return g;
    }
}
