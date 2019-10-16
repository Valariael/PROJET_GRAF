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
        List<Node> nodes2 = g3.getAllNodes();
        System.out.println("incident edges to " + nodes2.get(0).toString() + " : " + g3.getIncidentEdges(nodes2.get(0)));

        Graf currentGraf = null;
        List<Node> nodes;

        String choiceMenu;
        boolean stop = false, weightActivated = false, current;
        System.out.println("--GRAF PROJECT--\n");
        while (!stop) {
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
            System.out.println("11 : Traverse the graph in DFS");
            System.out.println("12 : Traverse the graph in BFS");
            System.out.println("'enter' to exit");
            System.out.println("----------------");
            if(currentGraf != null) {
                printGraf(currentGraf); // TODO indicate type of graph
                System.out.println("----------------");
            }

            choiceMenu = instructionAndChoice(null);
            switch (choiceMenu)
            {
                case "1":
                    instruction = "";
                    String graphTypeChoice;
                    current = false;
                    while (!current)
                    {
                        System.out.println();
                        System.out.println("---GRAPH TYPE---");
                        System.out.println("1 : Undirected");
                        System.out.println("2 : Directed");
                        System.out.println("3 : Undirected and weighted");
                        System.out.println("4 : Directed and weighted");
                        System.out.println("'enter' to go back");
                        System.out.println("----------------");

                        graphTypeChoice = instructionAndChoice(null);
                        switch (graphTypeChoice)
                        {
                            //TODO: ask for override current
                            case "1":
                                currentGraf = new UndirectedGraf();
                                weightActivated = false;
                                current = true;
                                instruction = "empty undirected graph created";
                                break;
                            case "2":
                                currentGraf = new Graf();
                                weightActivated = false;
                                current = true;
                                instruction = "empty directed graph created";
                                break;
                            case "3":
                                currentGraf = new UndirectedGraf();
                                weightActivated = true;
                                current = true;
                                instruction = "empty undirected and weighted graph created";
                                break;
                            case "4":
                                currentGraf = new Graf();
                                weightActivated = true;
                                current = true;
                                instruction = "empty directed and weighted graph created";
                                break;
                            case "5":
                                instruction = "";
                                String randomGraphChoice;
                                //TODO while
                                System.out.println();
                                System.out.println("--RANDOM GRAPH--");
                                //TODO: weight ?
                                System.out.println("1 : Create an empty graph");
                                System.out.println("2 : Add a node");
                                System.out.println("3 : Remove a node");
                                System.out.println("4 : Add an edge");
                                System.out.println("'enter' to go back");
                                System.out.println("----------------");

                                randomGraphChoice = instructionAndChoice(null);
                                switch (randomGraphChoice)
                                {
                                    //TODO
                                }
                                current = true;
                                break;
                            case "":
                                instruction = "returned";
                                current = true;
                                break;
                            default:
                                instruction = "'" + graphTypeChoice + "' is not a choice";
                                current = false;
                                break;
                        }
                    }
                break;
                case "2":
                    //TODO: return if no graph
                    instruction = "";
                    System.out.println();
                    System.out.println("--Creating node--");

                    current = false;
                    int id = -1;
                    while (!current) {
                        System.out.print("Enter node id (int) : ");
                        String choice = in.nextLine();
                        try {
                            id = Integer.parseInt(choice);
                            current = true;
                        } catch(NumberFormatException e) {
                            System.out.println("!- '" + choice + "' is not an integer");
                        }
                    }

                    System.out.print("Enter node name ('enter' for empty) : ");
                    String name = in.nextLine();

                    instruction = "added node(id: " + id;
                    if(!name.isEmpty()) {
                        instruction += ", name: " + name;
                        currentGraf.addNode(new Node(id, name));
                    } else
                        currentGraf.addNode(new Node(id));
                    instruction += ")";
                    break;
                case "3":
                    //TODO: return if no graph
                    nodes = currentGraf.getAllNodes();
                    nodes.sort(Comparator.comparing(Node::getId));

                    String choiceRemoveNode;
                    Node picked = null;
                    int n;
                    current = false;
                    while (!current) {
                        System.out.println();
                        System.out.println("--Removing a node--");
                        for (Node node : nodes) {
                            System.out.println(" " + node.toString());
                        }
                        System.out.println("----------------");
                        System.out.println("'enter' to return");
                        choiceRemoveNode = instructionAndChoice(("Enter the ID of the node to be removed : "));
                        if(choiceRemoveNode.equals("")) {
                            instruction = "returned";
                            break;
                        } else {
                            try {
                                n = Integer.parseInt(choiceRemoveNode);
                                for(Node node : nodes) {
                                    if(node.getId() == n) picked = node;
                                }
                                if(picked != null) {
                                    currentGraf.removeNode(picked);
                                    current = true;
                                } else instruction = "not a valid ID";
                            } catch(NumberFormatException e) {
                                instruction = "not a number";
                            }
                        }
                    }
                    break;
                case "4":
                    //TODO: return if no graph
                    current = false;
                    int idFrom = -1, idTo = -1;
                    while (!current) {
                        System.out.println();
                        System.out.println("--Adding an edge-- 1/2");
                        System.out.println("'enter' to return");
                        System.out.print("Enter node From id (int) : ");
                        String choiceAddNode = in.nextLine();

                        if(choiceAddNode.equals("")) {
                            instruction = "returned";
                            break;
                        } else {
                            try {
                                idFrom = Integer.parseInt(choiceAddNode);
                                current = true;
                            } catch(NumberFormatException e) {
                                System.out.println("!- '" + choiceAddNode + "' is not an integer");
                            }
                        }
                    }

                    current = false;
                    while (!current) {
                        System.out.println();
                        System.out.println("--Adding an edge-- 2/2");
                        System.out.println("'enter' to return");
                        System.out.print("Enter node To id (int) : ");
                        String choiceAddNode = in.nextLine();

                        if(choiceAddNode.equals("")) {
                            instruction = "returned";
                            break;
                        } else {
                            try {
                                idTo = Integer.parseInt(choiceAddNode);
                                current = true;
                            } catch (NumberFormatException e) {
                                System.out.println("!- '" + choiceAddNode + "' is not an integer");
                            }
                        }
                    }
                    //TODO weight
                    instruction = "added edge(from: " + idFrom + ", to: " + idTo + ")";
                    //TODO rework
                    currentGraf.addEdge(currentGraf.getKeyFromGraf(new Node(idFrom)), currentGraf.getKeyFromGraf(new Node(idTo)));
                    break;
                case "5":
                    //TODO: return if no graph
                    instruction = "";
                    current = false;
                    int nEdge = 0;
                    List<Edge> edges = null;
                    while(!current)
                    {
                        System.out.println();
                        System.out.println("--Removing an edge--");

                        edges = currentGraf.getAllEdges();
                        for (int i = 0; i < edges.size(); i++)
                        {
                            System.out.println(i + " : " + edges.get(i));
                        }
                        System.out.println("'enter' to return");
                        System.out.println("----------------");
                        String choiceRemoveEdge = instructionAndChoice(null);

                        if(choiceRemoveEdge.equals("")) {
                            instruction = "returned";
                            break;
                        } else {
                            try {
                                nEdge = Integer.parseInt(choiceRemoveEdge);
                                current = true;
                            } catch (NumberFormatException e) {
                                instruction = choiceRemoveEdge + "' is not an integer";
                            }
                        }
                    }

                    currentGraf.removeEdge(edges.get(nEdge).getHead(), edges.get(nEdge).getTail());
                    instruction = "removed edge(from: " + edges.get(nEdge).getHead().toString() + ", to: " + edges.get(nEdge).getTail().toString() + ")";
                    break;
                case "6":
                    //TODO: return if no graph
                    System.out.println();
                    System.out.println("--Printing the graph in DOT format--");
                    System.out.println();
                    System.out.println(currentGraf.toDotString());
                    System.out.println();
                    System.out.println("'enter' to go back");
                    in.nextLine();
                    break;
                case "7":
                    instruction = "";
                    current = false;
                    while (!current) {
                        System.out.println();
                        System.out.println("--Loading a graph from a DOT file--");
                        System.out.println("Warning : it will override the current graph");
                        System.out.println("'enter' to return");
                        String path = instructionAndChoice("Path to file : ");
                        if(path.equals("")) {
                            instruction = "returned";
                            break;
                        } else {
                            try {
                                currentGraf = getGrafFromDotFile(path);
                                current = true;
                                instruction = "graph loaded succesfully";
                            } catch (IOException e) {
                                instruction = "unable to read file " + path;
                            }
                        }
                    }
                    break;
                case "8":
                    //TODO: return if no graph
                    instruction = "";
                    current = false;
                    while (!current) {
                        System.out.println();
                        System.out.println("--Exporting a graph to a DOT file--");
                        System.out.println("'enter' to return");
                        String path = instructionAndChoice("Path or filename : ");
                        if(path.equals("")) {
                            instruction = "returned";
                            break;
                        } else {
                            try {
                                currentGraf.toDotFile(path);
                                current = true;
                                instruction = "graph saved succesfully";
                            } catch (IOException e) {
                                instruction = "unable to write file " + path;
                            }
                        }
                    }
                    break;
                case "9":
                    //TODO: return if no graph
                    instruction = "";
                    System.out.println();
                    System.out.println("--Reversing the graph--");
                    System.out.println();
                    currentGraf = currentGraf.getReverseGraph();
                    instruction = "graph reversed";
                    break;
                case "10":
                    //TODO: return if no graph
                    System.out.println();
                    System.out.println("--Computing the transitive closure of the graph--");
                    System.out.println();
                    System.out.println(currentGraf);
                    System.out.println();
                    System.out.println("'enter' to go back");
                    in.nextLine();
                    break;
                case "11":
                    //TODO: return if no graph
                    nodes = currentGraf.getAllNodes();
                    nodes.sort(Comparator.comparing(Node::getId));

                    String choiceNodeDFS;
                    Node startDFS = null;
                    int nDFS;
                    current = false;
                    while (!current) {
                        System.out.println();
                        System.out.println("--Traversing the graph in DFS--");
                        for (Node node : nodes) {
                            System.out.println(" " + node.toString());
                        }
                        System.out.println("----------------");
                        System.out.println("'enter' to return");
                        choiceNodeDFS = instructionAndChoice(("Enter the ID of the starting node for a DFS : "));
                        if(choiceNodeDFS.equals("")) {
                            instruction = "returned";
                            break;
                        } else {
                            try {
                                nDFS = Integer.parseInt(choiceNodeDFS);
                                for(Node node : nodes) {
                                    if(node.getId() == nDFS) startDFS = node;
                                }
                                if(startDFS != null) {
                                    System.out.println(currentGraf.getDFS(startDFS));
                                    current = true;
                                } else instruction = "not a valid ID";
                            } catch(NumberFormatException e) {
                                instruction = "not a number";
                            }
                        }
                    }
                    System.out.println();
                    System.out.println("'enter' to go back");
                    in.nextLine();
                    break;
                case "12":
                    //TODO: return if no graph
                    nodes = currentGraf.getAllNodes();
                    nodes.sort(Comparator.comparing(Node::getId));

                    String choiceNodeBFS;
                    Node startBFS = null;
                    int nBFS;
                    current = false;
                    while (!current) {
                        System.out.println();
                        System.out.println("--Traversing the graph in BFS--");
                        for (Node value : nodes) {
                            System.out.println(" " + value.toString());
                        }
                        System.out.println("----------------");
                        System.out.println("'enter' to return");
                        choiceNodeBFS = instructionAndChoice(("Enter the ID of the starting node for a BFS : "));
                        if(choiceNodeBFS.equals("")) {
                            instruction = "returned";
                            break;
                        } else {
                            try {
                                nBFS = Integer.parseInt(choiceNodeBFS);
                                for(Node node : nodes) {
                                    if(node.getId() == nBFS) startBFS = node;
                                }
                                if(startBFS != null) {
                                    System.out.println(currentGraf.getBFS(startBFS));
                                    current = true;
                                } else instruction = "not a valid ID";
                            } catch(NumberFormatException e) {
                                instruction = "not a number";
                            }
                        }
                    }
                    System.out.println();
                    System.out.println("'enter' to go back");
                    in.nextLine();
                    break;
                case "":
                    //TODO: maybe ask if leave when graph unsaved
                    System.out.println("--ENDING PROGRAM--");
                    stop = true;
                    break;
                default:
                    instruction = "'" + choiceMenu + "' is not a choice";
                    break;
            }
        }
    }

    public static String instructionAndChoice(String str) {
        if(!instruction.equals(""))
            System.out.println("!- " + instruction);
        if(str != null)
            System.out.print(str);
        else
            System.out.print("Your choice : ");
        String choice = in.nextLine();
        System.out.println();
        instruction = "";
        return choice;
    }

    public static void printGraf(Graf g) {
        System.out.println("printing graph :");
        if(g.adjList.isEmpty()) System.out.println(" - empty - ");
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
