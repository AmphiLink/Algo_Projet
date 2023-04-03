import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static void print(Object o) {
        System.out.println(o);
    }

    public static void main(String[] args) throws IOException {
        ArrayList<ArrayList<String>> flowers = readFile(args[0]);
        for (ArrayList<String> listeFleurs : flowers) {
            String tooMuch = naive(listeFleurs);
            print(tooMuch);
        }
    }

    public static ArrayList<ArrayList<String>> readFile(String path) throws IOException {

        File file = new File(path);
        Scanner obj = new Scanner(file);

        ArrayList<ArrayList<String>> globList = new ArrayList<ArrayList<String>>();

        int i = 0;
        while (obj.hasNextLine()) {
            String line = obj.nextLine();

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
        obj.close();
        return globList;
    }

    public static String naive(ArrayList<String> listeFleurs) {
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
}
