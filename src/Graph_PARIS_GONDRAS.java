import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by franck on 5/11/16.
 */
public class Graph_PARIS_GONDRAS {

    public static void main(String[] args) {

        //Imports file and creates graph structure

        ArrayList<String> words = importFile(new String("motsdelongueur6.txt"));
        ArrayList<ArrayList<String>> graph = buildGraph (words);
        System.out.println(graph);

        //Number of vertices
        System.out.println("Nombre de sommets : " + numberOfVertices(words));

        //Number of edges
        System.out.println("Nombre d'arrêtes : " + numberOfEdges(graph));

        //Number of isolated vertices
        System.out.println("Nombre de sommets isolés : " + numberOfIsolatedVertices(graph));

        //Number of connected components
        System.out.println("Nombre de composantes connexes : " + numberOfConnectedComponents(words, graph));

        //Number of vertices with k neighbours
        numberOfVerticesKNeighbours(graph);

        //Maximum number of neighbours
        System.out.println("Nombre maximal de voisins pour un sommet : " + getMaxNumberNeighbours(graph));


    }

    private static ArrayList<String> importFile(String filename) {
        //Import file
        ArrayList<String> words = new ArrayList<>();
        try {
            for (String line : Files.readAllLines(Paths.get(filename))) {
                words.add(line);
            }
            System.out.println(words);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

    private static ArrayList<ArrayList<String>> buildGraph (ArrayList<String> words){

        ArrayList<ArrayList<String>> graph = new ArrayList<>();

        //Create graph
        for (String word : words){
            ArrayList<String> neighbours = new ArrayList<>();
            for(String word2 : words){
                if(compareWords(word, word2) == 1){
                    neighbours.add(word2);
                }
            }
            graph.add(neighbours);
        }
        return graph;
    }

    private static int compareWords(String str1, String str2){
        int cpt =0;
        for(int i=0; i<6; i++){
            if(str1.charAt(i)!= str2.charAt(i)){
                cpt++;
            }
        }
        return cpt;
    }

    private static int numberOfVertices(ArrayList<String> words){
        return words.size();
    }

    private static int numberOfEdges (ArrayList<ArrayList<String>> graph){
        int number = 0;
        for (ArrayList<String> neighbours : graph){
            number+= neighbours.size();
        }
        return number/2; // Non oriented graph
    }

    private static int numberOfIsolatedVertices (ArrayList<ArrayList<String>> graph){
        int number = 0;
        for (ArrayList<String> neighbours : graph){
            if(neighbours.size() == 0){
                number++;
            }
        }
        return number;
    }


    private static int numberOfConnectedComponents(ArrayList<String> words, ArrayList<ArrayList<String>> graph){
        int color = 0;
        int temp;
        ArrayList<Integer> file = new ArrayList<>();

        ArrayList<Integer> coloration = new ArrayList<>();
        for(int i=0; i<words.size(); i++){
            coloration.add(0);
        }

        file.add(0);

        for(int i = 0 ; i < words.size() ; i++){
            if(coloration.get(i) == 0) {
                color++;
                coloration.set(i, color);
                file.add(i);
                while (!file.isEmpty()) {
                    temp = file.get(0);
                    file.remove(0);
                    for (String word : graph.get(temp)) {
                        if (coloration.get(getWordPosition(words, word)) == 0) {
                            file.add(getWordPosition(words, word));
                            coloration.set(getWordPosition(words, word), color);
                        }
                    }
                }
            }
        }
        return color;
    }

    private static int getWordPosition (ArrayList<String> words, String word){
        int t = words.indexOf(word);
        return t;
    }

    private static void numberOfVerticesKNeighbours(ArrayList<ArrayList<String>> graph){
        int cpt = 0;

        for(int i=0; i<getMaxNumberNeighbours(graph); i++){
            for(ArrayList neighbours : graph){
                if(neighbours.size() == i){
                    cpt++;
                }
            }
            System.out.println("Nombre de sommets ayant " + i + " voisins : " + cpt);
            cpt = 0;
        }
    }

    private static int getMaxNumberNeighbours (ArrayList<ArrayList<String>> graph){
        int max = 0;
        for(ArrayList<String> neighbours : graph){
            if (neighbours.size() > max){
                max = neighbours.size();
            }
        }
        return max;
    }
}
