public class Node {
    private int id;
    private String name;

    public Node(String name) {
        this.name = name;
    }

    public Node(int id) {
        this.id = id;
    }

    public Node(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String toString() {
        return "node id : " + this.id + ", node name : " + this.name + ";";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Node)) {
            return false;
        }
        Node otherNode = (Node) obj;
        return id == otherNode.getId();
    }
}
