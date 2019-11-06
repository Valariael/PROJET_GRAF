import java.io.*;
import java.util.*;

public class Main {

    private static String instruction = "";
    private static Scanner in = new Scanner(System.in);
    private static boolean weightActivated = false;

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

        UndirectedGraf g3 = UndirectedGraf.randomGrafBuilder(2, 1, true);
        Graf g4 = Graf.randomGrafBuilder(1, 1, true);
        Graf g5 = Graf.randomDagBuilder(0, 1);
        System.out.println();
        printGraf(g3);
        System.out.println();
        System.out.println("successor array : " + Arrays.toString(g3.getSuccessorArray()));
        try {
            g3.toDotFile("random_connected_graf_non_dense.dot");
            g4.toDotFile("random_connected_graf_directed_non_dense.dot");
            g5.toDotFile("random_dag.dot");
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
        boolean stop = false, current;
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
            System.out.println("13 : Generate random graphs");
            System.out.println("'enter' to exit");
            System.out.println("----------------");
            if(currentGraf != null) {
                System.out.println(currentGraf.toDotString()); //TODO: replace with improved method
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
                        if(currentGraf != null) System.out.println("Warning : it will override the current graph");
                        System.out.println("'enter' to go back");
                        System.out.println("----------------");

                        graphTypeChoice = instructionAndChoice(null);
                        switch (graphTypeChoice)
                        {
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
                    if(currentGraf == null) {
                        instruction = "create a graph first";
                        continue;
                    }
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

                    //TODO: check existence of node with same id and replace below with tostring
                    instruction = "added node(id: " + id;
                    if(!name.isEmpty()) {
                        instruction += ", name: " + name;
                        currentGraf.addNode(new Node(id, name));
                    } else
                        currentGraf.addNode(new Node(id));
                    instruction += ")";
                    break;

                case "3":
                    if(currentGraf == null) {
                        instruction = "create a graph first";
                        continue;
                    }
                    nodes = currentGraf.getAllNodes();
                    if(nodes.isEmpty()) {
                        instruction = "add a node first";
                        continue;
                    }
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
                        if(choiceRemoveNode.isEmpty()) {
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
                                } else {
                                    instruction = "not a valid ID";
                                }
                            } catch(NumberFormatException e) {
                                instruction = "not a number";
                            }
                        }
                    }
                    break;

                case "4":
                    if(currentGraf == null) {
                        instruction = "create a graph first";
                        continue;
                    }
                    if(currentGraf.getAllNodes().isEmpty()) {
                        instruction = "add a node first";
                        continue;
                    }

                    current = false;
                    boolean skip = false;
                    int idFrom = -1, idTo = -1, weight = -1;
                    while (!current && !skip) {
                        System.out.println();
                        System.out.println("--Adding an edge-- 1/" + (weightActivated?"3":"2"));
                        System.out.println("'enter' to return");
                        System.out.print("Enter node From id (int) : ");
                        String choiceAddNode = in.nextLine();

                        if(choiceAddNode.isEmpty()) {
                            instruction = "returned";
                            skip = true;
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
                    while (!current && !skip) {
                        System.out.println();
                        System.out.println("--Adding an edge-- 2/" + (weightActivated?"3":"2"));
                        System.out.println("'enter' to return");
                        System.out.print("Enter node To id (int) : ");
                        String choiceAddNode = in.nextLine();

                        if(choiceAddNode.isEmpty()) {
                            instruction = "returned";
                            skip = true;
                        } else {
                            try {
                                idTo = Integer.parseInt(choiceAddNode);
                                current = true;
                            } catch (NumberFormatException e) {
                                System.out.println("!- '" + choiceAddNode + "' is not an integer");
                            }
                        }
                    }

                    if(weightActivated) {
                        current = false;
                        while (!current && !skip) {
                            System.out.println();
                            System.out.println("--Adding an edge-- 3/3");
                            System.out.println("'enter' to return");
                            System.out.print("Enter weight of the edge (int) : ");
                            String choiceAddNode = in.nextLine();

                            if(choiceAddNode.isEmpty()) {
                                instruction = "returned";
                                skip = true;
                            } else {
                                try {
                                    weight = Integer.parseInt(choiceAddNode);
                                    current = true;
                                } catch (NumberFormatException e) {
                                    System.out.println("!- '" + choiceAddNode + "' is not an integer");
                                }
                            }
                        }
                    }

                    if(idTo != -1 && idFrom != -1 && !weightActivated) {
                        Node fromNode = currentGraf.getKeyFromGraf(new Node(idFrom));
                        Node toNode = currentGraf.getKeyFromGraf(new Node(idTo));
                        currentGraf.addEdge(fromNode, toNode);
                        instruction = "added Edge from " + fromNode.toString() + " to " + toNode.toString();
                    } else if(idTo != -1 && idFrom != -1) {
                        Node fromNode = currentGraf.getKeyFromGraf(new Node(idFrom));
                        Node toNode = currentGraf.getKeyFromGraf(new Node(idTo));
                        toNode.setToWeightActivated(true);
                        toNode.setToLabel(weight);
                        currentGraf.addEdge(fromNode, toNode);
                        instruction = "added Edge from " + fromNode.toString() + " to " + toNode.toString() + " (weight=" + weight + ")";
                    }
                    break;

                case "5":
                    if(currentGraf == null) {
                        instruction = "create a graph first";
                        continue;
                    }
                    if(currentGraf.getAllEdges().isEmpty()) {
                        instruction = "add an edge first";
                        continue;
                    }

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
                            System.out.println(i + " : " + edges.get(i).toString());
                        }
                        System.out.println("'enter' to return");
                        System.out.println("----------------");
                        String choiceRemoveEdge = instructionAndChoice(null);

                        if(choiceRemoveEdge.isEmpty()) {
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
                    instruction = "removed " + edges.get(nEdge).toString();
                    break;

                case "6":
                    if(currentGraf == null) {
                        instruction = "create a graph first";
                        continue;
                    }
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
                        if(currentGraf != null) System.out.println("Warning : it will override the current graph");
                        System.out.println("'enter' to return");
                        String path = instructionAndChoice("Path to file : ");
                        if(path.isEmpty()) {
                            instruction = "returned";
                            break;
                        } else {
                            try {
                                currentGraf = getGrafFromDotFile(path);
                                current = true;
                                instruction = "graph loaded successfully";
                            } catch (IOException e) {
                                System.out.println(e.toString());
                                instruction = "unable to read file " + path;
                            }
                        }
                    }
                    break;

                case "8":
                    if(currentGraf == null) {
                        instruction = "create a graph first";
                        continue;
                    }
                    instruction = "";
                    current = false;
                    while (!current) {
                        System.out.println();
                        System.out.println("--Exporting a graph to a DOT file--");
                        System.out.println("'enter' to return");
                        String path = instructionAndChoice("Path or filename : ");
                        if(path.isEmpty()) {
                            instruction = "returned";
                            break;
                        } else {
                            try {
                                currentGraf.toDotFile(path);
                                current = true;
                                instruction = "graph saved succesfully";
                            } catch (IOException e) {
                                System.out.println(e.toString());
                                instruction = "unable to write file " + path;
                            }
                        }
                    }
                    break;

                case "9":
                    if(currentGraf == null) {
                        instruction = "create a graph first";
                        continue;
                    }
                    instruction = "";
                    System.out.println();
                    System.out.println("--Reversing the graph--");
                    System.out.println();
                    currentGraf = currentGraf.getReverseGraph();
                    instruction = "graph reversed";
                    break;

                case "10":
                    if(currentGraf == null) {
                        instruction = "create a graph first";
                        continue;
                    }
                    System.out.println();
                    System.out.println("--Computing the transitive closure of the graph--");
                    System.out.println();
                    System.out.println(currentGraf.getTransitiveClosure().toDotString());
                    System.out.println();
                    System.out.println("'enter' to go back");
                    in.nextLine();
                    break;

                case "11":
                    if(currentGraf == null) {
                        instruction = "create a graph first";
                        continue;
                    }
                    nodes = currentGraf.getAllNodes();
                    if(nodes.isEmpty()) {
                        instruction = "add a node first";
                        continue;
                    }
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
                        System.out.println("'enter' to compute a DFS starting at the smallest ID");
                        choiceNodeDFS = instructionAndChoice(("Enter the ID of the starting node for a DFS : "));
                        if(choiceNodeDFS.isEmpty()) {
                            System.out.println(currentGraf.getDFS());
                            current = true;
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
                    if(currentGraf == null) {
                        instruction = "create a graph first";
                        continue;
                    }
                    nodes = currentGraf.getAllNodes();
                    if(nodes.isEmpty()) {
                        instruction = "add a node first";
                        continue;
                    }
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
                        System.out.println("'enter' to compute a BFS starting at the smallest ID");
                        choiceNodeBFS = instructionAndChoice(("Enter the ID of the starting node for a BFS : "));
                        if(choiceNodeBFS.isEmpty()) {
                            System.out.println(currentGraf.getBFS());
                            current = true;
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

                case "13":
                    instruction = "";
                    String randomGraphChoice;
                    boolean currentRandom = false;
                    while (!currentRandom) {
                        System.out.println();
                        System.out.println("--RANDOM GRAPH--");
                        //TODO: weight ?
                        System.out.println("1 : Generate a random directed graf");
                        System.out.println("2 : Generate a random undirected graf");
                        System.out.println("3 : Generate a random dag");
                        if(currentGraf != null) System.out.println("Warning : it will override the current graph");
                        System.out.println("'enter' to go back");
                        System.out.println("----------------");
                        int size = -1;
                        double density = -1, edgeProbability = -1;
                        boolean connected = false;
                        randomGraphChoice = instructionAndChoice(null);
                        if (randomGraphChoice.length() == 1 && "123".contains(randomGraphChoice)) {
                            System.out.println("--RANDOM GRAPH OPTIONS--");
                            do {
                                boolean warn = false;
                                System.out.print("Set the graf size: ");
                                try {
                                    size = Integer.parseInt(in.nextLine());
                                }catch (NumberFormatException e) {
                                    size = -1;
                                    warn = true;
                                    System.out.println("!- must be a strictly positive integer");
                                }
                                if (size <= 0) {
                                    size = -1;
                                    if (!warn) System.out.println("!- must be a strictly positive integer");
                                }
                            } while(size == -1);
                            System.out.println("--RANDOM GRAPH OPTIONS--");
                            if (randomGraphChoice.equals("3")) {
                                do {
                                    boolean warn = false;
                                    System.out.print("Set the edge probability: ");
                                    try {
                                        edgeProbability = Double.parseDouble(in.nextLine()); // ...
                                    }catch (NumberFormatException e) {
                                        warn = true;
                                       edgeProbability = -1;
                                       System.out.println("!- must be a valid double in range [0-1]");
                                    }
                                    if (edgeProbability < 0 || edgeProbability > 1) {
                                        edgeProbability = -1;
                                        if (!warn) System.out.println("!- must be a valid double in range [0-1]");
                                    }
                                } while (edgeProbability == -1);
                            }
                            else {
                                do {
                                    boolean warn = false;
                                    System.out.print("Set the graf density: ");
                                    try {
                                        density = Double.parseDouble(in.nextLine()); // ...
                                    } catch (NumberFormatException e) {
                                        warn = true;
                                        density = -1;
                                        System.out.println("!- must be a valid double in range [0-1]");
                                    }
                                    if (density < 0 || density > 1) {
                                        density = -1;
                                        if (!warn) System.out.println("!- must be a valid double in range [0-1]");
                                    }
                                }while(density == -1);
                                System.out.println("--RANDOM GRAPH OPTIONS--");
                                boolean valid = false;
                                do {
                                    System.out.print("Set if the graf is connected [t/f]: ");
                                    String connectionChoice = in.nextLine();
                                    if (connectionChoice.equals("t")) {
                                        connected = true;
                                        valid = true;
                                    } else if (connectionChoice.equals("f")) {
                                        connected = false;
                                        valid = true;
                                    }
                                    else {
                                        System.out.println("!- valid answers : [t/f]");
                                    }
                                } while(!valid);
                            }
                            if (randomGraphChoice.equals("1")) {
                                currentGraf = Graf.randomGrafBuilder(size, density, connected);
                            }
                            else if (randomGraphChoice.equals("2")) {
                                currentGraf = UndirectedGraf.randomGrafBuilder(size, density, connected);
                            }
                            else if (randomGraphChoice.equals("3")) {
                                currentGraf = Graf.randomDagBuilder(size, edgeProbability);
                            }
                            currentRandom = true;
                        }
                        else if (randomGraphChoice.equals("")) {
                            instruction = "returned";
                            currentRandom = true;
                        }
                        else {
                            instruction = "'" + randomGraphChoice + "' is not a choice";
                            current = false;
                        }
                        current = true;
                    }
                    break;

                case "":
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
        if(!instruction.isEmpty())
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

    public static void printGraf(Graf g) { // TODO : improve
        System.out.println("printing " + (g instanceof UndirectedGraf?"graph":"digraph") + " :");
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
                    weightActivated = true;
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
