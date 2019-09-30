public class Main {

    public static void main(String[] args) {

        Graf g = new Graf(2,0,4,0,3,0,2,1);
        g.adjList.forEach((key, value) -> System.out.println(key + " " + value.toString()));

    }
}
