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

    public List<Node> getDFS() {
        return null;
    }

    public List<Node> getBFS() {
        return null;
    }
}
