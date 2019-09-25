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


}
