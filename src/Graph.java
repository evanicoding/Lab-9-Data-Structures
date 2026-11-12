import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Graph {
    private ArrayList<ArrayList<Edge>> list;

    public Graph(){
        list = new ArrayList<>();
    }

    public Graph(int vertices){
        list = new ArrayList<>();
        for (int i = 0; i<vertices;i++){
            list.add(new ArrayList<>());
        }
    }

    public ArrayList<String> getVertices(String filename) throws FileNotFoundException {
        ArrayList<String> vertices = new ArrayList<>();

        try (Scanner in = new Scanner(new File(filename))) {
            while (in.hasNextLine()) {
                String line = in.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\s+");
                if (parts.length < 3) continue;

                if (!vertices.contains(parts[0])) vertices.add(parts[0]);
                if (!vertices.contains(parts[1])) vertices.add(parts[1]);
            }
        }
        return vertices;
    }

    public void readEdges(String filename, ArrayList<String> vertices) throws FileNotFoundException{
        try (Scanner in = new Scanner(new File(filename))) {
            while (in.hasNextLine()) {
                String line = in.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\s+");
                if (parts.length < 3) continue;

                int src = vertices.indexOf(parts[0]);
                int dst = vertices.indexOf(parts[1]);
                double weight = Double.parseDouble(parts[2]);

                list.get(src).add(new Edge(dst,weight));
                list.get(dst).add(new Edge(src,weight));
            }
        }
    }

    public void addVertices(ArrayList<String> vertices){
        for (int i = 0;i<vertices.size();i++){
            list.add(new ArrayList<>());
        }
    }

    public void printList(){
        for (int i = 0;i<list.size();i++){
            System.out.print((char) ('A' + i) + " ");
            for(Edge e : list.get(i)){
                System.out.print((char) ('A' + e.dst) + ":" + e.weight + " ");
            }
            System.out.println();
        }
    }

}
