import java.io.*;
import java.util.*;

public class Graf {
    HashMap<Node, ArrayList<Node>> adjList;

    public Graf(int... succesorArray) {
        adjList = new HashMap<Node, ArrayList<Node>>();

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
        adjList = new HashMap<Node, ArrayList<Node>>();
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
        //rm edges
    }

    public boolean containsNode(Node node) {
        return adjList.containsKey(node);
    }

    public void addEdge(Node from, Node to) {
        if (containsNode(from) && containsNode(to)) {
            ArrayList<Node> adjNodes = adjList.get(from);
            adjNodes.add(to);
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
        return null;
    }

    public List<Node> getAllNodes() {
        return null;
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

    public int[] getSuccessorArray() {
        return null;
    }

    public int[][] getAdjMatrix() {
        return null;
    }

    public Graf getReverseGraph() {
        return null;
    }

    public Graf getTransitiveClosure() {
        return null;
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
}
