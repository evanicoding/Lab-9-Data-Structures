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

            graph.printList();

        } catch (FileNotFoundException e) {
            System.out.println("Unknown file");
        }
    }
}
