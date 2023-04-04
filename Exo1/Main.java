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

        HashMap<String, Integer> counts;
        ArrayList<ArrayList<String>> flowers = readFile(args[0]);

        print("=== Méthode Naïve ===");
        for (ArrayList<String> listeFleurs : flowers) {
            counts = new HashMap<>();
            print(naive(listeFleurs, counts));
        }

        print("\n=== Méthode Récursive ===");
        for (ArrayList<String> listeFleurs : flowers) {
            counts = new HashMap<>();
            print(recursive(listeFleurs, counts, 0, null));
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

    public static String naive(ArrayList<String> listeFleurs, HashMap<String, Integer> counts) {
        // @ requires listeFleurs != null;
        // @ ensures \result != null;
        // @ assignable System.out;
        // @ pure;
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

    // Spécifie cette fonction recursive en JML
    public static String recursive(ArrayList<String> listeFleurs, HashMap<String, Integer> counts, int current_plant,
            String invasivePlant) {
        // @ requires listeFleurs != null;
        // @ requires counts != null;
        // @ requires current_plant >= 0;
        // @ ensures \result != null;
        // @ assignable System.out;
        // @ pure;
        String plant = listeFleurs.get(current_plant);
        counts.put(plant, counts.getOrDefault(plant, 0) + 1);
        if (counts.get(plant) > listeFleurs.size() / 2) {
            invasivePlant = plant;
        }
        if (++current_plant < listeFleurs.size()) {
            invasivePlant = recursive(listeFleurs, counts, current_plant, invasivePlant);
        }
        return invasivePlant;
    }
}
