import java.util.*;

public class Graf {
    Map<Node, List<Node>> adjList;

    public Graf() {
        adjList = new HashMap<Node, List<Node>>() {
            @Override
            public boolean equals(Object o) {
                return false;
            }

            @Override
            public int hashCode() {
                return 0;
            }
        };
    }
}
