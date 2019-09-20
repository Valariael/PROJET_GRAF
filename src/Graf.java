import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graf {
    Map<Node, List<Node>> adjList;

    public Graf() {
        adjList = new Map<Node, List<Node>>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean containsKey(Object key) {
                return false;
            }

            @Override
            public boolean containsValue(Object value) {
                return false;
            }

            @Override
            public List<Node> get(Object key) {
                return null;
            }

            @Override
            public List<Node> put(Node key, List<Node> value) {
                return null;
            }

            @Override
            public List<Node> remove(Object key) {
                return null;
            }

            @Override
            public void putAll(Map<? extends Node, ? extends List<Node>> m) {

            }

            @Override
            public void clear() {

            }

            @Override
            public Set<Node> keySet() {
                return null;
            }

            @Override
            public Collection<List<Node>> values() {
                return null;
            }

            @Override
            public Set<Entry<Node, List<Node>>> entrySet() {
                return null;
            }

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
