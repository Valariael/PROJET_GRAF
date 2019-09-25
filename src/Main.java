public class Main {

    public static void main(String[] args) {

        Graf g = new Graf(2,0,4,0,3,0,2,1);
        g.adjList.entrySet().forEach(entry->{
            System.out.print(entry.getKey().getId() + " ");
            entry.getValue().forEach(n->{
                System.out.print(" " + n.getId());
            });
            System.out.print("\n");
        });

    }
}
