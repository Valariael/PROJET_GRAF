import java.io.*;
import java.util.*;

public class Graf {
    HashMap<Node, ArrayList<Node>> adjList;

    public Graf(int... succesorArray) {
        adjList = new HashMap<>();

        int n = 1;
        // creating first node
        Node currentNode = new Node(n);
        adjList.put(currentNode, new ArrayList<Node>() {});

        // looping through succesor array to create the other nodes
        for (int nodeNumber : succesorArray) {
            //create new node if zero read
            if(nodeNumber == 0) {
                n++;
                currentNode = new Node(n);
                adjList.put(currentNode, new ArrayList<Node>() {});
            //add node to adjacency list otherwise
            } else {
                ArrayList<Node> nodes = adjList.get(currentNode);
                nodes.add(new Node(nodeNumber));
            }
        }
    }

    public Graf() {
        adjList = new HashMap<>();
    }

    public void addNode(Node node) {
        adjList.put(node, new ArrayList<>());
    }

    public void addNode(int n) {
        Node node = new Node(n);
        adjList.put(node, new ArrayList<>());
    }

    public void removeNode(Node node) {
        adjList.remove(node);
        //TODO remove all edges
    }

    public boolean containsNode(Node node) {
        return adjList.containsKey(node);
    }

    public Node getKeyFromGraf(Node node) {
        Node n = null;

        for(Node key : this.adjList.keySet()) {
            if(key.getId() == node.getId()) return key;
        }
        return null;
    }

    public void addEdge(Node from, Node to) {
        if (containsNode(from) && containsNode(to)) {
            ArrayList<Node> adjNodes = adjList.get(from);
            adjNodes.add(to);
        }
    }

    public void addEdge(Edge edge) {
        if (containsNode(edge.getHead()) && containsNode(edge.getTail())) {
            adjList.get(edge.getTail()).add(edge.getHead());
        }
    }

    public void removeEdge(Node from, Node to) {
        if (containsNode(from) && containsNode(to)) {
            adjList.get(from).remove(to);
        }
    }

    public List<Node> getSuccessors(Node node) {
        return adjList.get(node);
    }

    public List<Edge> getOutEdges(Node node) {
        List<Node> outNodes = adjList.get(node);
        List<Edge> outEdges = new ArrayList<>();
        for (Node n : outNodes) {
            outEdges.add(new Edge(node, n));
        }
        return outEdges;
    }

    public List<Edge> getInEdges(Node node) {
        List<Edge> inEdges = new ArrayList<>();
        for (Map.Entry<Node, ArrayList<Node>> nodeEntry : adjList.entrySet()) {
            if (nodeEntry.getValue().contains(node)) {
                inEdges.add(new Edge(nodeEntry.getKey(), node));
            }
        }
        return inEdges;
    }

    public List<Edge> getIncidentEdges(Node node) {
        List<Edge> edges = getInEdges(node);
        edges.addAll(getOutEdges(node));

        return edges;
    }

    public List<Node> getAllNodes() {
        return new ArrayList<>(this.adjList.keySet());
    }

    public List<Edge> getAllEdges() {
        List<Edge> edges = new ArrayList<>();
        for (Map.Entry<Node, ArrayList<Node>> nodeEntry : adjList.entrySet()) {
            for (Node node : nodeEntry.getValue()) {
                edges.add(new Edge(nodeEntry.getKey(), node));
            }
        }

        return edges;
    }

    public List<Edge> getAllPossibleEdges() {
        List<Edge> possible_edges = new ArrayList<>();
        for (Node node_from : adjList.keySet()) {
            for (Node node_to : adjList.keySet()) {
                if (!node_from.equals(node_to)) {
                    possible_edges.add(new Edge(node_from, node_to));
                }
            }
        }
        return possible_edges;
    }

    public int[] getSuccessorArray() {
        List<Node> nodes = new ArrayList<>(this.getAllNodes());
        List<Integer> list = new ArrayList<>();

        nodes.sort(Comparator.comparing(Node::getId));

        nodes.forEach(node -> {
            final List<Node> successors = this.adjList.get(node);
            successors.forEach(nodeTo -> list.add(nodeTo.getId()));
            list.add(0);
        });
        list.remove(list.size()-1);

        int[] array = new int[list.size()];
        for(int i = 0; i<list.size(); i++) array[i] = list.get(i);

        return array;
    }

    public int[][] getAdjMatrix() {
        int nodeCount = this.adjList.keySet().size();
        int[][] matrix = new int[nodeCount][nodeCount];
        List<Node> nodes = new ArrayList<>(this.adjList.keySet());

        // sorting nodes in case there is a gap between node numbers
        nodes.sort(Comparator.comparing(Node::getId));

        this.adjList.forEach((nodeFrom, nodeList) -> nodeList.forEach((nodeTo) -> {
            if(nodeTo.getToLabel() >= 0) matrix[nodes.indexOf(nodeFrom)][nodes.indexOf(nodeTo)] = nodeTo.getToLabel();
            else matrix[nodes.indexOf(nodeFrom)][nodes.indexOf(nodeTo)] = 1;
        }));

        return matrix;
    }

    public Graf getReverseGraph() {
        Graf reverse = new Graf();

        for(Node n : this.getAllNodes()) reverse.addNode(new Node(n.getId()));

        this.adjList.forEach((nodeFrom, nodeList) -> nodeList.forEach((nodeTo) -> {
            final Node n = reverse.getKeyFromGraf(nodeTo);

            Node to = reverse.getKeyFromGraf(nodeFrom);
            to.setToLabel(nodeFrom.getToLabel());
            to.setName(nodeFrom.getName());

            reverse.addEdge(n, to);
        }));

        return reverse;
    }

    public Graf getTransitiveClosure() {
        return null;//TODO
    }

    public List<Node> getDFS(Node startingNode) {
        if(startingNode == null) {
            startingNode = Collections.min(this.adjList.keySet(), Comparator.comparing(Node::getId));
        }

        List<Node> visited = new ArrayList<>();
        Stack<Node> stack = new Stack<>();

        stack.push(startingNode);

        while(!stack.isEmpty()) {
            Node currentNode = stack.pop();

            if(!visited.contains(currentNode)) {
                visited.add(currentNode);

                for(Node n : this.adjList.get(currentNode)) {
                    stack.push(n);
                }
            }
        }

        return visited;
    }

    public List<Node> getBFS(Node startingNode) {
        if(startingNode == null) {
            startingNode = Collections.min(this.adjList.keySet(), Comparator.comparing(Node::getId));
        }

        List<Node> visited = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();

        queue.add(startingNode);
        visited.add(startingNode);

        while(!queue.isEmpty()) {
            Node currentNode = queue.poll();

            for(Node n : this.adjList.get(currentNode)) {
                if(!visited.contains(n)) {
                    visited.add(n);
                    queue.add(n);
                }
            }
        }

        return visited;
    }

    public String toDotString() {
        StringBuilder sb = new StringBuilder();

        if(this instanceof UndirectedGraf) sb.append("graph g {\n");
        else sb.append("digraph g {\n");

        this.adjList.forEach((nodeFrom, nodeList) -> nodeList.forEach((nodeTo -> {
            sb.append(" ");

            sb.append(nodeFrom.getId());
            if(this instanceof UndirectedGraf) sb.append(" -- ");
            else sb.append(" -> ");
            sb.append(nodeTo.getId());

            int n;
            if((n = nodeTo.getToLabel()) >= 0) {
                sb.append(" [label=");
                sb.append(n);
                sb.append("]");
            }

            sb.append(";\n");
        })));

        sb.append("}");

        return sb.toString();
    }

    public void toDotFile(String path) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.write(this.toDotString());
        writer.close();
    }

    public static Graf randomGrafBuilder(int size, double density, boolean connected) {

        Graf randomGraf = new Graf();

        List<Edge> possible_edges;

        if (connected) { // create a path containing all vertices
            List<Node> unvisited = new ArrayList<>();
            for (int i = 0; i < size; i++) { // Adding vertices to the graf and to the unvisited list, used to trace the path
                Node newNode = new Node(i+1);
                randomGraf.addNode(newNode);
                unvisited.add(newNode);
            }
            possible_edges = randomGraf.getAllPossibleEdges();
            int unvisitedSize = unvisited.size();
            int firstIndex = (int) (Math.random() * unvisitedSize);
            Node currentNode = unvisited.get(firstIndex);
            Node firstNode = currentNode;  // Keep the first in memory to loop at the end
            unvisited.remove(currentNode);
            unvisitedSize--;
            while (unvisitedSize > 0) {
                int randomNextIndex = (int) (Math.random() * unvisitedSize);
                Node randomNextNode = unvisited.get(randomNextIndex);
                randomGraf.addEdge(currentNode, randomNextNode);
                unvisited.remove(randomNextNode);
                possible_edges.remove(new Edge(currentNode, randomNextNode));
                currentNode = randomNextNode;
                unvisitedSize--;
            }
            randomGraf.addEdge(currentNode, firstNode);  // Loop to the first node
            possible_edges.remove(new Edge(currentNode, firstNode));
        }
        else {
            for (int i = 0; i < size; i++) { // Just adding vertices to the graf
                Node newNode = new Node(i+1);
                randomGraf.addNode(newNode);
            }
            possible_edges = randomGraf.getAllPossibleEdges();
        }

        if (density < 0) {
            density = 0;
        }
        if (density > 1) {
            density = 1;
        }
        int needed_edges = (int) (size * (size - 1) * density);
        if (connected) {
            needed_edges = needed_edges - size;
        }
        if (needed_edges < 1) {
            return randomGraf;
        }

        int possible_edges_nb = possible_edges.size();
        while (needed_edges > 0) {
            Edge randomEdge = possible_edges.get((int)(Math.random() * possible_edges_nb));
            randomGraf.addEdge(randomEdge);
            possible_edges.remove(randomEdge);
            possible_edges_nb--;
            needed_edges--;
        }

        return randomGraf;

    }

}
