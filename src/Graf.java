import java.util.*;

public class Graf {
    HashMap<Node, ArrayList<Node>> adjList;

    public Graf(int... nodeNumbers) {
        adjList = new HashMap<Node, ArrayList<Node>>() {
            @Override
            public boolean equals(Object o) {
                return false;
            }

            @Override
            public int hashCode() {
                return 0;
            }
        };

        int n = 1;
        Node currentNode = new Node(n);
        adjList.put(currentNode, new ArrayList<>() {});
        for (int nodeNumber : nodeNumbers) {
            if(nodeNumber == 0) {
                n++;
                currentNode = new Node(n);
                adjList.put(currentNode, new ArrayList<>() {});
            }

            ArrayList<Node> nodes = adjList.get(currentNode);
            nodes.add(new Node(n));
            adjList.put(currentNode, nodes);
        }
    }

    public void addNode(Node node) {
        adjList.put(node, new ArrayList<>());
        return;
    }

    public void removeNode(Node node) {
        adjList.remove(node);
        return;
    }

    public boolean containsNode(Node node) {
        return adjList.containsKey(node);
    }

    public void addEdge(Node from, Node to) {
        if (containsNode(from) && containsNode(to)) {
            List adjNodes = adjList.get(from);
            adjNodes.add(to);
        }
    }

    public void removeEdge(Node from, Node to) {
        return;
    }

    public List<Node> getSuccessors(Node node) {
        return null;
    }

    public List<Edge> getOutEdges(Node node) {
        return null;
    }

    public List<Edge> getInEdges(Node node) {
        return null;
    }

    public List<Edge> getIncidentEdges(Node node) {
        return null;
    }

    public List<Node> getAllNodes() {
        return null;
    }

    public List<Edge> getAllEdges() {
        return null;
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

    public String toDotString() {
        return null;
    }

    public void toDotFile(String path) {
        return;
    }

}
