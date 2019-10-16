import java.io.*;
import java.util.*;

public class Main {

    private static String instruction = "";
    private static Scanner in = new Scanner(System.in);

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
        Graf g4 = Graf.randomGrafBuilder(5, 0, true);
        System.out.println();
        printGraf(g3);
        System.out.println();
        System.out.println("successor array : " + Arrays.toString(g3.getSuccessorArray()));
        try {
            g3.toDotFile("random_connected_graf_non_dense.dot");
            g4.toDotFile("random_connected_graf_directed_non_dense.dot");
        }catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
        printGraf(g3.getReverseGraph());
        System.out.println();
        List<Node> nodes = g3.getAllNodes();
        System.out.println("incident edges to " + nodes.get(0).toString() + " : " + g3.getIncidentEdges(nodes.get(0)));

        Graf currentGraf;

        String choiceMenu;
        boolean stop = false, weightActivated = false;
        System.out.println("--GRAF PROJECT--\n");
        while (!stop)
        {
            System.out.println("------MENU------");
            System.out.println("1 : Create an empty graph");
            System.out.println("2 : Add a node");
            System.out.println("3 : Remove a node");
            System.out.println("4 : Add an edge");
            System.out.println("5 : Remove an edge");
            System.out.println("6 : Print the graph in DOT format");
            System.out.println("7 : Load a graph from a DOT file");
            System.out.println("8 : Export a graph to a DOT file");
            System.out.println("9 : Reverse the graph");
            System.out.println("10 : Compute the transitive closure of the graph");
            System.out.println("12 : Traverse the graph in DFS");
            System.out.println("11 : Traverse the graph in BFS");
            System.out.println("'enter' to exit");
            System.out.println("----------------");

            choiceMenu = instructionAndChoice();
            switch (choiceMenu)
            {
                case "1":
                    String graphTypeChoice;
                    System.out.println("---GRAPH TYPE---");
                    System.out.println("1 : Undirected");
                    System.out.println("2 : Directed");
                    System.out.println("3 : Undirected and weighted");
                    System.out.println("4 : Directed and weighted");
                    System.out.println("'enter' to return");
                    System.out.println("----------------");

                    graphTypeChoice =  instructionAndChoice();
                    switch (graphTypeChoice)
                    {
                        case "1":
                            currentGraf = new UndirectedGraf();
                            instruction = "empty undirected graph created";
                            break;
                        case "2":
                            currentGraf = new Graf();
                            instruction = "empty directed graph created";
                            break;
                        case "3":
                            currentGraf = new UndirectedGraf();
                            instruction = "empty undirected and weighted graph created";
                            break;
                        case "4":
                            currentGraf = new Graf();
                            instruction = "empty directed and weighted graph created";
                            break;
                        case "":
                            instruction = "returned";
                            break;
                        default:
                            instruction = "'" + graphTypeChoice + "' is not a choice";
                            break;
                    }
                break;
                case "2":
                    System.out.println("Sous menu 2");
                    break;
                case "3":
                    System.out.println("Sous menu 2");
                    break;
                case "":
                    stop = true;
                    break;
                default:
                    instruction = "'" + choiceMenu + "' is not a choice";
                    break;
            }
        }
    }

    public static String instructionAndChoice() {
        if(!instruction.equals("")) System.out.println("!- " + instruction);
        System.out.print("Your choice : ");
        String choice = in.nextLine();
        System.out.println();
        return choice;
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
