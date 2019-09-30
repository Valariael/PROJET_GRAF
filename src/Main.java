public class Main {

    public static void main(String[] args) {

        Graf g = new Graf(2,4,0,0,6,0,2,3,5,8,0,0,4,7,0,3,0,7);
        g.adjList.forEach((key, value) -> System.out.println(key + " " + value.toString()));

    }
}
