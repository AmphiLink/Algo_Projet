import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;

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
            String tooMuch = naive(listeFleurs);
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

   /* public static String recursive(ArrayList<String> listeFleurs){
        ArrayList<String> listeFleursTotaux = new ArrayList<>();
        HashMap<String, Integer> counts = new HashMap<String, Integer>();
        for (String plant : listeFleurs) {
            counts.put(plant, counts.getOrDefault(plant, 0) + 1);
        }
        if (listeFleurs.size() == 1){
            return listeFleurs.get(0);
        }

        if(counts.get(listeFleurs.get(0)) > counts.get(listeFleurs.get(1))){
            listeFleurs.remove(listeFleurs.get(1));
            return recursive(listeFleurs);
        }
        else if (counts.get(listeFleurs.get(0)) < counts.get(listeFleurs.get(1))){
            listeFleurs.remove(listeFleurs.get(0));
            return recursive(listeFleurs);
        }

        else{
            int last_index = listeFleurs.size() - 1;
            String tmp = listeFleurs.get(1);
            String var2 = listeFleurs.get(last_index);
            listeFleurs.get(last_index) = tmp;

        }
    }*/
}
