import java.io.*;

public class Main {

    public static void main(String[] args) {

        Graf g1 = new Graf(2,4,0,0,6,0,2,3,5,8,0,0,4,7,0,3,0,7);
        g1.adjList.forEach((key, value) -> System.out.println(key + " " + value.toString()));

        try
        {
            Graf g2 = getGrafFromDotFile("");
            g2.adjList.forEach((key, value) -> System.out.println(key + " " + value.toString()));
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public static Graf getGrafFromDotFile(String pathToFile) throws IOException
    {
        File file = new File(pathToFile);
        BufferedReader br = new BufferedReader(new FileReader(file));

        Graf g = null;
        String s;

        while ((s = br.readLine()) != null) {
            System.out.println("read : " + s);

            if(s.length() <= 2) System.out.println("> empty line read");
            else if(s.contains("#")) System.out.println("> commented line read");
            else if(s.contains("digraph")) g = new Graf();
            else if(s.contains("graph")) g = new UndirectedGraf();
            else {
                String[] parts = s.split(" ");

                Node from = new Node(Integer.parseInt(parts[0]));
                Node to;

                if(parts.length <= 3) {
                    to = new Node(Integer.parseInt(parts[2].substring(0,parts[2].length()-2)));
                } else {
                    int toWeight = Integer.parseInt(parts[3].substring(8,parts[3].length()-3));
                    to = new Node(Integer.parseInt(parts[2]), toWeight);
                }

                if(g instanceof UndirectedGraf) {
                    if(!g.containsNode(from)) g.addNode(from);
                    if(!g.containsNode(to)) g.addNode(to);

                    g.addEdge(from, to);
                    g.addEdge(to, from);
                } else if(g != null){
                    if(!g.containsNode(from)) g.addNode(from);
                    if(!g.containsNode(to)) g.addNode(to);

                    g.addEdge(from, to);
                }
            }
        }

        return g;
    }
}
