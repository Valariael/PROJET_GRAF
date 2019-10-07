public class Edge implements Comparable<Edge> {
    private int weight;
    private String label;
    private Node head, tail;

    public Edge(Node head, Node tail, int weight, String label) {
        this.weight = weight;
        this.label = label;
        this.head = head;
        this.tail = tail;
    }

    public Edge(Node head, Node tail) {
        this(head, tail, 1, ""+head.getId()+"->"+tail.getId());
    }

    @Override
    public int compareTo(Edge o) {
        return 0;
    }
}
