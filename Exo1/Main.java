import java.io.File;
import java.io.IOException;
import java.util.*;

//Spécifie chaque procédure en JML
public class Main {

    public static void print(Object o) {
        // @ requires o != null;
        // @ ensures true;
        // @ assignable System.out;

        System.out.println(o);
    }

    public static void main(String[] args) throws IOException {
        // @ requires args.length == 1;
        // @ ensures true;
        // @ assignable System.out;
        // @ throws IOException;

        ArrayList<ArrayList<String>> flowers = readFile(args[0]);
        for (ArrayList<String> listeFleurs : flowers) {
            String tooMuch = recursive(listeFleurs);
            print(tooMuch);
        }

    }

    public static ArrayList<ArrayList<String>> readFile(String path) throws IOException {
        // @ requires path != null;
        // @ ensures \result != null;
        // @ assignable System.out;
        // @ throws IOException;
        // @ pure;

        File file = new File(path);
        Scanner scanner = new Scanner(file);

        ArrayList<ArrayList<String>> globList = new ArrayList<ArrayList<String>>();

        int i = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (i == 0 || i % 2 == 1) {
                i++;
                continue;
            } else {
                String[] tabValues = line.split(" ");
                ArrayList<String> listeFleurs = new ArrayList<String>();
                for (String value : tabValues) {
                    listeFleurs.add(value);
                }
                globList.add(listeFleurs);
            }
            i++;
        }
        scanner.close();
        return globList;
    }

    public static String naive(ArrayList<String> listeFleurs) {
        // @ requires listeFleurs != null;
        // @ ensures \result != null;
        // @ assignable System.out;
        // @ pure;

        HashMap<String, Integer> counts = new HashMap<String, Integer>();
        for (String plant : listeFleurs) {
            counts.put(plant, counts.getOrDefault(plant, 0) + 1);
        }

        String tooMuch = null;
        for (String plant : counts.keySet()) {
            if (counts.get(plant) > listeFleurs.size() / 2) {
                tooMuch = plant;
                break;
            }
        }
        return tooMuch;
    }

    public static String recursive(ArrayList<String> listeFleurs){
        HashMap<String, Integer> counts = new HashMap<String, Integer>();
        int current_index = 0;
        return recursiveHelp(counts, listeFleurs, current_index, null);
    }

    private static String recursiveHelp(HashMap<String, Integer> counts, ArrayList<String> listeFleurs, int current_plant, String invasivePlant){
            String plant = listeFleurs.get(current_plant);
            counts.put(plant, counts.getOrDefault(plant, 0) + 1);
            if (counts.get(plant) > listeFleurs.size()/2){
                invasivePlant = plant;
            }
            if (++current_plant < listeFleurs.size()){
               invasivePlant = recursiveHelp(counts, listeFleurs, current_plant, invasivePlant);
            }
            return invasivePlant;
    }
}
