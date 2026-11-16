import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Graph {
    private ArrayList<ArrayList<Edge>> list;
    private ArrayList<String> names;

    public Graph(){
        list = new ArrayList<>();
        names = new ArrayList<>();
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
        names = vertices;
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
            System.out.print((char) ('A' + i) + " -> ");
            for(Edge e : list.get(i)){
                System.out.print((char) ('A' + e.dst) + ":" + e.weight + " ");
            }
            System.out.println();
        }
    }

    public void printNodes(){
        System.out.println("List of Nodes: ");
        for (String node:names){
            System.out.println(node);
        }
    }

    public void dijkstra(String start){
        int src = names.indexOf(String.valueOf(start));
        if (src == -1) {
            System.out.println("Unknown node: " + start);
            return;
        }

        PriorityQueue<double[]> priority = new PriorityQueue<>((a, b) -> Double.compare(a[0], b[0]));

        ArrayList<Double> distance = new ArrayList<>();
        ArrayList<Integer> prev = new ArrayList<>();

        for (ArrayList<Edge> v : list){
            distance.add(Double.POSITIVE_INFINITY);
            prev.add(null);
        }

        distance.set(src,0.0);
        priority.offer(new double[]{0,src});

        while(!priority.isEmpty()){
            double[] curr = priority.poll();
            int u = (int) curr[1];
            if (curr[0] > distance.get(u)) continue;

            for (Edge e: list.get(u)){
                double alt = distance.get(u) + e.weight;
                if (alt < distance.get(e.dst)){
                    distance.set(e.dst,alt);
                    prev.set(e.dst,u);
                    priority.offer(new double[]{alt,e.dst});
                }
            }
        }

        System.out.println("Node to start at: " + names.get(src));
        System.out.println();

        for (int v = 0; v < list.size(); v++) {
            if (distance.get(v) == Double.POSITIVE_INFINITY) {
                System.out.println(names.get(v) + "  unreachable");
                continue;
            }

            // walk prev backward from v to src, then reverse
            ArrayList<Integer> path = new ArrayList<>();
            for (Integer at = v; at != null; at = prev.get(at)) path.add(at);
            Collections.reverse(path);

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < path.size(); i++) {
                if (i > 0) sb.append(" -> ");
                sb.append(names.get(path.get(i)));
            }
            System.out.println(sb + "  dist: " + (distance.get(v)));
        }

    }

}
