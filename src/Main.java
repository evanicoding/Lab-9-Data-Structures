import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Graph graph = new Graph();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a file name: ");

        String file = scanner.nextLine();

        try{
            graph.addVertices(graph.getVertices(file));

            graph.readEdges(file,graph.getVertices(file));

            System.out.println();
            graph.printNodes();

            while (true){
                System.out.println("\nWhat node would you like to start at? ('End' to quit): ");
                String start = scanner.next();

                if (start.equalsIgnoreCase( "End")){
                    break;
                }

                graph.dijkstra(start.toUpperCase());
            }


        } catch (FileNotFoundException e) {
            System.out.println("Unknown file");
        }
    }
}
