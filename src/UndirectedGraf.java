import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UndirectedGraf extends Graf {

    public static UndirectedGraf randomConnectedGrafBuilder(int size) {
        UndirectedGraf randomGraf = new UndirectedGraf();
        List<Node> unvisited = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Node newNode = new Node(i);
            randomGraf.addNode(newNode);
            unvisited.add(newNode);
        }
        int unvisitedSize = unvisited.size();
        int firstIndex = (int) (Math.random() * unvisitedSize);
        Node currentNode = unvisited.get(firstIndex);
        unvisited.remove(currentNode);
        unvisitedSize--;
        while (unvisitedSize > 0) {
            int randomNextIndex = (int) (Math.random() * unvisitedSize);
            Node randomNextNode = unvisited.get(randomNextIndex);
            randomGraf.addEdge(currentNode, randomNextNode);
            unvisited.remove(randomNextNode);
            currentNode = randomNextNode;
            unvisitedSize--;
        }
        List<Node> allNodes = randomGraf.getAllNodes();
        for (Map.Entry<Node, ArrayList<Node>> nodeListEntry : randomGraf.adjList.entrySet()) {
            int randEdgesNumber = (int) (Math.random() * size);
            for (int i = 0; i < randEdgesNumber; i++) {
                Node randomNextNodePotential = allNodes.get((int)(Math.random() * size));
                if (nodeListEntry.getValue().contains(randomNextNodePotential)) {
                    continue;
                }
                if (randomGraf.adjList.get(randomNextNodePotential).contains(nodeListEntry.getKey())) {
                    continue;
                }
                nodeListEntry.getValue().add(randomNextNodePotential);
            }
        }
        return randomGraf;
    }

}
